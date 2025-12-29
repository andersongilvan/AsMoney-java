package AsMoney.swaggerResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record NotFound(
        @Schema(example = "Resource not found.")
        String message) {
}
