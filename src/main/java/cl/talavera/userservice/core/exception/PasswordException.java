package cl.talavera.userservice.core.exception;

public class PasswordException extends RuntimeException {
    public PasswordException() {
        super("Password Invalid");
    }
}
