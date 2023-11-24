package cl.talavera.userservice.core.service;


import cl.talavera.userservice.config.security.JWTService;
import cl.talavera.userservice.core.exception.EmailException;
import cl.talavera.userservice.core.exception.PasswordException;
import cl.talavera.userservice.core.model.domain.User;
import cl.talavera.userservice.core.model.UserSignUpRequest;
import cl.talavera.userservice.core.model.UserSignUpResponse;
import cl.talavera.userservice.core.port.IRepositoryHandler;
import cl.talavera.userservice.core.port.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;


@Service
@AllArgsConstructor
public class UserService implements IUserService {

    Clock clock;
    JWTService jwtService;
    RequestValidation requestValidation;
    IRepositoryHandler repositoryHandler;


    @Override
    public UserSignUpResponse signup(UserSignUpRequest request) {
        String passwordPattern = "^(?=(?:[^0-9]*[0-9]){2})(?=.*[A-Z])[a-zA-Z0-9]{8,12}$";
        requestValidation.setPasswordPattern(passwordPattern);

        repositoryHandler.findByEmail(request.getEmail());


        if (!requestValidation.validPassword(request.getPassword())) {
            throw new PasswordException();
        }

        if (!requestValidation.validMail(request.getEmail())) {
            throw new EmailException();
        }


        LocalDate today = LocalDate.now(clock);
        String token = jwtService.getJWTToken(request.getName());

        User user = User.builder()
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
            repositoryHandler.savePhone(p);
        });


        User userSaved = repositoryHandler.saveUser(user);

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
