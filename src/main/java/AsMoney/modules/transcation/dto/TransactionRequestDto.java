package AsMoney.modules.transcation.dto;

import AsMoney.modules.transcation.enums.AmountType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequestDto(
        @NotBlank(message = "The field 'title' is required")
        @Size(min = 6, message = "The field 'title' must contain at least 6 characters.")
        String title,

        @NotBlank(message = "The field 'description' is required")
        @Size(min = 10, message = "The field 'description' must contain at least 12 characters.")
        String description,

        @NotNull(message = "The field 'amount' is required")
        @Positive(message = "The field 'amount' must be greater than zero.")
        BigDecimal amount,

        @NotNull(message = "The type of 'transaction' must be specified.")
        AmountType amountType,

        UUID userId

) {
}
