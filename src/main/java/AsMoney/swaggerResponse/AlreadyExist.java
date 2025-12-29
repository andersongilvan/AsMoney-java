package AsMoney.swaggerResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record AlreadyExist(@Schema(example = "E-mail already exist.")
                           String message) {
}
