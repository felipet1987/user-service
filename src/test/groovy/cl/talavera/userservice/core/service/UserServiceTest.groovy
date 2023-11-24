package cl.talavera.userservice.core.service

import cl.talavera.userservice.adapter.secundary.repository.PhoneRepository
import cl.talavera.userservice.adapter.secundary.repository.UserRepository
import cl.talavera.userservice.adapter.secundary.repository.entity.PhoneDao
import cl.talavera.userservice.adapter.secundary.repository.entity.UserDao
import cl.talavera.userservice.config.security.JWTService
import cl.talavera.userservice.core.exception.EmailException
import cl.talavera.userservice.core.exception.ExistedException
import cl.talavera.userservice.core.exception.PasswordException
import cl.talavera.userservice.core.model.PhoneRequest
import cl.talavera.userservice.core.model.UserSignUpRequest
import cl.talavera.userservice.core.model.UserSignUpResponse
import spock.lang.Specification

import java.time.*
import java.time.format.DateTimeFormatter

class UserServiceTest extends Specification {
    UserService service;
    UserRepository userRepository
    PhoneRepository phoneRepository
    JWTService jwtService
    Clock clock
    RequestValidation requestValidation


    void setup() {
        String stringDate = "09:15:30 PM, Sun 10/09/2022";
        String pattern = "hh:mm:ss a, EEE M/d/uuuu";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        LocalDateTime localDateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        clock = Clock.fixed(instant, ZoneId.systemDefault());


        userRepository = Mock()
        phoneRepository = Mock()
        jwtService = Stub()
        requestValidation = Mock()


        service = new UserService(userRepository, phoneRepository, clock, jwtService, requestValidation)
    }

    void cleanup() {

    }

    def "Signup"() {

        def phones = List.of(PhoneRequest.builder()
                .number("")
                .countrycode("")
                .citycode("")
                .build())
        UserSignUpRequest request = UserSignUpRequest.builder()
                .password("a2asfGfdfdf4")
                .name("fulanito")
                .email("email")
                .phones(phones)
                .build();




        def now = LocalDate.now(clock)
        given:
        UserDao user = UserDao.builder()
                .token("token")
                .password("a2asfGfdfdf4")
                .name("fulanito")
                .email("email")
                .created(now)
                .last_login(now)
                .modified(now)
                .isActive(true)
                .build()

        def uuid = UUID.randomUUID()
        UserDao userMock = UserDao.builder()
                .token("token")
                .name("fulanito")
                .isActive(true)
                .id(uuid)
                .created(now)
                .last_login(now)
                .modified(now)
                .build()

        jwtService.getJWTToken("fulanito") >> "token"

        userRepository.save(user) >> userMock

        userRepository.findByEmail("email") >> Optional.empty()
        requestValidation.validPassword("a2asfGfdfdf4") >> true
        requestValidation.validMail("email") >> true

        when:
        UserSignUpResponse response = service.signup(request)

        then:
        1 * phoneRepository.save(PhoneDao.builder()
                .citycode("")
                .number("")
                .countrycode("")
                .build())
        response.getIsActive() == true
        response.getToken() == "token"
        response.getId() == uuid.toString()
        response.getCreated() == now
        response.getModified() == now
        response.getLast_login() == now

    }

    def "thrown ExistedException"() {
        given:
        UserSignUpRequest request = UserSignUpRequest.builder()
                .password("a2asfGfdfdf4")
                .name("fulanito")
                .email("email")
                .build();




        userRepository.findByEmail("email") >> Optional.of(UserDao.builder().build())

        when:
        service.signup(request)

        then:
        thrown ExistedException
    }

    def "thrown PasswordException"() {
        given:
        UserSignUpRequest request = UserSignUpRequest.builder()
                .password("a2asfGfdfdf4")
                .name("fulanito")
                .email("email")
                .build();
        userRepository.findByEmail("email") >> Optional.empty()

        requestValidation.validPassword("a2asfGfdfdf4") >> false

        when:
        service.signup(request)

        then:
        thrown PasswordException
    }

    def "thrown email Exception"() {
        given:
        UserSignUpRequest request = UserSignUpRequest.builder()
                .password("a2asfGfdfdf4")
                .name("fulanito")
                .email("email")
                .build();

        userRepository.findByEmail("email") >> Optional.empty()
        requestValidation.validPassword("a2asfGfdfdf4") >> true
        requestValidation.validMail("email") >> false

        when:
        service.signup(request)

        then:
        thrown EmailException
    }
}
