package AsMoney.modules.auth.user;

public record AuthUserRequest(
        String email,
        String password
) {
}
