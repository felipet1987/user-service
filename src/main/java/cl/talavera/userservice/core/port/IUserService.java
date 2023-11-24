package cl.talavera.userservice.core.port;

import cl.talavera.userservice.core.model.UserSignUpRequest;
import cl.talavera.userservice.core.model.UserSignUpResponse;

public interface IUserService {
    UserSignUpResponse signup(UserSignUpRequest request);

}
