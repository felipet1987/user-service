package cl.talavera.userservice.adapter.secundary.repository;

import cl.talavera.userservice.adapter.secundary.repository.entity.PhoneDao;
import cl.talavera.userservice.adapter.secundary.repository.entity.UserDao;
import cl.talavera.userservice.core.exception.ExistedException;
import cl.talavera.userservice.core.model.PhoneRequest;
import cl.talavera.userservice.core.model.domain.User;
import cl.talavera.userservice.core.port.IRepositoryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RepositoryHandler implements IRepositoryHandler {

    UserRepository userRepository;
    PhoneRepository phoneRepository;

    public void findByEmail(String email){
        Optional<UserDao> byEmail = userRepository.findByEmail(email);
        byEmail.ifPresent(a -> {
            throw new ExistedException();
        });
    }
    public User saveUser(User userSignUp){

        UserDao user = UserDao.builder()
                .token(userSignUp.getToken())
                .password(userSignUp.getPassword())
                .created(userSignUp.getCreated())
                .last_login(userSignUp.getLast_login())
                .modified(userSignUp.getModified())
                .name(userSignUp.getName())
                .email(userSignUp.getEmail())
                .isActive(true)
                .build();
        UserDao userSaved = userRepository.save(user);

        return User.builder()
                .id(userSaved.getId())
                .isActive(userSaved.getIsActive())
                .token(userSaved.getToken())
                .created(userSaved.getCreated())
                .last_login(userSaved.getLast_login())
                .modified(userSaved.getModified())
                .build();

    }

    public void savePhone(PhoneRequest phone) {
        phoneRepository.save(PhoneDao.builder()
                .countrycode(phone.getCountrycode())
                .number(phone.getNumber())
                .citycode(phone.getCitycode())
                .build());
    }
}
