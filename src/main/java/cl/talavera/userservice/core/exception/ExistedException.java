package cl.talavera.userservice.core.exception;

public class ExistedException extends RuntimeException {
    public ExistedException() {
        super("El correo ya registrado");
    }
}
