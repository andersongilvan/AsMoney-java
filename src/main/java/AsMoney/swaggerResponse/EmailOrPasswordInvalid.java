package AsMoney.swaggerResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmailOrPasswordInvalid(@Schema(example = "E-mail or password invalid.")
                                     String message) {
}
