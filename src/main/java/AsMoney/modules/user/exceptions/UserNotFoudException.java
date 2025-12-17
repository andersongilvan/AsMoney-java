package AsMoney.modules.user.exceptions;

public class UserNotFoudException extends RuntimeException{
    public UserNotFoudException() {
        super("User not found.");
    }
}
