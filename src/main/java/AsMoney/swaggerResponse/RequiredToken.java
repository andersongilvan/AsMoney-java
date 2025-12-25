package AsMoney.swaggerResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record RequiredToken(
        @Schema(example = "REQUIRED_TOKEN")
        String error,
        @Schema(example = "Token not found")
        String message
) {
}
