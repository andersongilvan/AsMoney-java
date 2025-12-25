package AsMoney.swaggerResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record ValidationErrors(
        @Schema(example = "VALIDATION_ERRORS")
        String error,
        @Schema(example = "The field 'sampleExample' is required")
        String message
) {
}
