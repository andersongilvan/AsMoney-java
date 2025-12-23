package AsMoney.config.security.jwt.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super("Invalid token");
    }
}
