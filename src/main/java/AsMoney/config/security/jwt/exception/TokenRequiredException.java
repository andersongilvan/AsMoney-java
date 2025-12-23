package AsMoney.config.security.jwt.exception;

public class TokenRequiredException extends RuntimeException{
    public TokenRequiredException() {
        super("Token is required");
    }
}
