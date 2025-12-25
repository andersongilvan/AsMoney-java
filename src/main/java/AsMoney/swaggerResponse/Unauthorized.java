package AsMoney.swaggerResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record Unauthorized(
        @Schema(example = "INVALID_TOKEN")
        String error,
        @Schema(example = "Invalid or expired token")
        String message
) {
}
