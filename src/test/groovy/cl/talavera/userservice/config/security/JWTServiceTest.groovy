package cl.talavera.userservice.config.security

import spock.lang.Specification

class JWTServiceTest extends Specification {
    JWTService jwtService
    void setup() {
        jwtService = new JWTService()
    }

    def"get token"(){

        String token = jwtService.getJWTToken("name")
        expect:
        token
    }
}
