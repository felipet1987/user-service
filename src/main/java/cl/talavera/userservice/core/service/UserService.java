package cl.talavera.userservice.core.service;

import cl.talavera.userservice.adapter.secundary.repository.PhoneRepository;
import cl.talavera.userservice.adapter.secundary.repository.UserRepository;
import cl.talavera.userservice.adapter.secundary.repository.entity.PhoneDao;
import cl.talavera.userservice.adapter.secundary.repository.entity.UserDao;
import cl.talavera.userservice.config.security.JWTService;
import cl.talavera.userservice.core.exception.EmailException;
import cl.talavera.userservice.core.exception.ExistedException;
import cl.talavera.userservice.core.exception.PasswordException;
import cl.talavera.userservice.core.model.UserSignUpRequest;
import cl.talavera.userservice.core.model.UserSignUpResponse;
import cl.talavera.userservice.core.port.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService implements IUserService {

    UserRepository userRepository;
    PhoneRepository phoneRepository;
    Clock clock;
    JWTService jwtService;
    RequestValidation requestValidation;


    @Override
    public UserSignUpResponse signup(UserSignUpRequest request) {
        String passwordPattern = "^(?=(?:[^0-9]*[0-9]){2})(?=.*[A-Z])[a-zA-Z0-9]{8,12}$";
        requestValidation.setPasswordPattern(passwordPattern);

        Optional<UserDao> byEmail = userRepository.findByEmail(request.getEmail());
        byEmail.ifPresent(a -> {
            throw new ExistedException();
        });

        if (!requestValidation.validPassword(request.getPassword())) {
            throw new PasswordException();
        }

        if (!requestValidation.validMail(request.getEmail())) {
            throw new EmailException();
        }


        LocalDate today = LocalDate.now(clock);
        String token = jwtService.getJWTToken(request.getName());
        UserDao user = UserDao.builder()
                .token(token)
                .password(request.getPassword())
                .created(today)
                .last_login(today)
                .modified(today)
                .name(request.getName())
                .email(request.getEmail())
                .isActive(true)
                .build();

        request.getPhones().forEach(p -> {
            phoneRepository.save(PhoneDao.builder()
                    .countrycode(p.getCountrycode())
                    .number(p.getNumber())
                    .citycode(p.getCitycode())
                    .build());
        });


        UserDao userSaved = userRepository.save(user);

        return UserSignUpResponse.builder()
                .id(userSaved.getId().toString())
                .isActive(userSaved.getIsActive())
                .token(token)
                .created(userSaved.getCreated())
                .last_login(userSaved.getLast_login())
                .modified(userSaved.getModified())
                .build();
    }


}
