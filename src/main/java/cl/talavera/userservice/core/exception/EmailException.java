package cl.talavera.userservice.core.exception;

public class EmailException extends RuntimeException {
    public EmailException() {
        super("Email Invalid");
    }
}
