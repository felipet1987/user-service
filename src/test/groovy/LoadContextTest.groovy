package cl.talavera.userservice

import cl.talavera.userservice.adapter.secundary.controller.UserController
import cl.talavera.userservice.adapter.secundary.repository.UserRepository
import cl.talavera.userservice.core.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired (required = false)
    private UserController userController

    @Autowired (required = false)
    private UserService userService

    @Autowired (required = false)
    private UserRepository userRepository

    def "when context is loaded then all expected beans are created"() {
        expect:
        userRepository
        userService
        userController
    }
}