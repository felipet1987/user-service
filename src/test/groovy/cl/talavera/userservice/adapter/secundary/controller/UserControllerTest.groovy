package cl.talavera.userservice.adapter.secundary.controller

import cl.talavera.userservice.core.exception.EmailException
import cl.talavera.userservice.core.exception.ExistedException
import cl.talavera.userservice.core.exception.PasswordException
import cl.talavera.userservice.core.model.UserSignUpRequest
import cl.talavera.userservice.core.model.UserSignUpResponse
import cl.talavera.userservice.core.port.IUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

import java.time.LocalDate

class UserControllerTest extends Specification {

    UserController userController
    IUserService userService

    void setup() {
        userService = Mock()
        userController = new UserController(userService)
    }

    void cleanup() {
    }

    def "SignUp"() {
        UserSignUpRequest request = UserSignUpRequest.builder().build()

        UserSignUpResponse expected = UserSignUpResponse.builder()
                .id("id")
                .token("token")
        .isActive(true)
        .modified(LocalDate.now())
                .build()

        userService.signup(request) >> expected


        UserSignUpResponse response = userController.signUp(request)

        expect:
        response.getToken() == response.getToken()
        response.getId() == response.getId()
        response.getIsActive() == response.getIsActive()
    }

    def "Email Exception"() {
        ResponseEntity<HttpError> error = userController.handleException(new EmailException())
        expect:
        error.body.getCodigo() == 500
        error.body.getDetail() == "Email Invalid"
    }

    def "Password Exception"() {
        ResponseEntity<HttpError> error = userController.handleException(new PasswordException())
        expect:
        error.body.getCodigo() == 500
    }

    def "Existe Exception"() {
        ResponseEntity<HttpError> error = userController.handleException(new ExistedException())
        expect:
        error.body.getCodigo() == 500
    }
    def "handle 400"() {
        ResponseEntity<HttpError> error = userController.handleNotFoundError(new ResponseStatusException(HttpStatus.NOT_FOUND))
        expect:
        error.body.codigo == 400
    }
}
