package AsMoney.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "The field 'name' is required")
        @Size(max = 20, min = 6, message = "The field 'name' must contain between 6 and 20 characters")
        String name,

        @Email(message = "The field 'e-mail' must be an e-mail valid")
        String email,

        @NotBlank(message = "The field 'password' is required")
        @Size(max = 15, min = 6, message = "The field 'password' must contain between 6 and 15 characters")
        String password
) {
}
