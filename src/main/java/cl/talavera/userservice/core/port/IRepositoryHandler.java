package cl.talavera.userservice.core.port;

import cl.talavera.userservice.core.model.PhoneRequest;
import cl.talavera.userservice.core.model.domain.User;

public interface IRepositoryHandler {
    void findByEmail(String email);

    void savePhone(PhoneRequest p);

    User saveUser(User user);
}
