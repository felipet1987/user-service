package cl.talavera.userservice.core.service

import spock.lang.Specification

class RequestValidationTest extends Specification {
    RequestValidation validation
    void setup() {
        validation = new RequestValidation()
    }

    def "InvalidMail"() {
        def isValid = validation.validMail("email@email.cl")
        expect:
        isValid
    }

    def "InvalidPassword"() {
        String  pasword_pattern = ""
        validation.setPasswordPattern(pasword_pattern)
        def isValid = validation.validPassword("")
        expect:
        isValid
    }
}
