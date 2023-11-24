package cl.talavera.userservice.adapter.secundary.repository

import cl.talavera.userservice.adapter.secundary.repository.entity.PhoneDao
import cl.talavera.userservice.adapter.secundary.repository.entity.UserDao
import cl.talavera.userservice.core.model.PhoneRequest
import cl.talavera.userservice.core.model.domain.User
import spock.lang.Specification

import java.time.LocalDate

class RepositoryHandlerTest extends Specification {

    PhoneRepository phoneRepository
    UserRepository userRepository
    RepositoryHandler repositoryHandler

    void setup() {
        phoneRepository = Mock()
        userRepository = Mock()
        repositoryHandler = new RepositoryHandler(userRepository, phoneRepository)
    }

    def "FindByEmail"() {
        when:
        repositoryHandler.findByEmail("email")
        then:
        1 * userRepository.findByEmail("email") >> Optional.empty()
    }

    def "SaveUser"() {

        LocalDate today = LocalDate.now()

        User user = User.builder()
                .token("")
                .password("")
                .created(today)
                .last_login(today)
                .modified(today)
                .name("")
                .email("")
                .isActive(true)
                .build();
        UserDao userDao = UserDao
                .builder()
                .token("")
                .password("")
                .created(today)
                .last_login(today)
                .modified(today)
                .name("")
                .email("")
                .isActive(true)
                .build()
        when:
        repositoryHandler.saveUser(user)

        then:
        1 * userRepository.save(userDao) >> UserDao
                .builder()
                .id(UUID.randomUUID())
                .build()
    }

    def "SavePhone"() {
        when:
        repositoryHandler.savePhone(PhoneRequest.builder().build())

        then:
        1*phoneRepository.save(PhoneDao.builder().build())
    }
}
