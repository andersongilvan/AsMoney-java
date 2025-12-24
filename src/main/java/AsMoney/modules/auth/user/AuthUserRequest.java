package AsMoney.modules.auth.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthUserRequest(
        @Email(message = "The field 'e-mail' must be an e-mail valid")
        String email,
        @NotBlank(message = "The field 'password' is required")
        String password
) {
}
