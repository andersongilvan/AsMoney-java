package AsMoney.modules.transcation.controllers;

import AsMoney.config.security.jwt.TokenData;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.useCases.DeleteTransactionUseCase;
import AsMoney.swaggerResponse.NotFound;
import AsMoney.swaggerResponse.RequiredToken;
import AsMoney.swaggerResponse.Unauthorized;
import AsMoney.swaggerResponse.ValidationErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/asmoney/transaction")


public class Delete {

    private final DeleteTransactionUseCase delete;


    @Operation(summary = "Delete a Transaction",
            description =

                    "### Delete a transaction of authenticated user"+
                    "\n\n" +
                    "## Return Flow\n" +
                            "Returns the details of a specific transaction.\n" +
                            "### Success:" +
                            "\n**204:** No Content\n" +
                            "\n" +
                            "### Possible Errors:" +
                            "\n" +
                            "**401**: Missing or invalid token.\n" +
                            "\n" +
                            "**403**: Forbidden")

    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204",
                            description = "No Content",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Transaction not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFound.class))),

                    @ApiResponse(responseCode = "401", description = "Unauthenticated user",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RequiredToken.class))),

                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Unauthorized.class)))
            }
    )


    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> handler(@AuthenticationPrincipal TokenData tokenData,
                                        @PathVariable UUID transactionId) {

        UUID userId = UUID.fromString(tokenData.id());

        this.delete.execute(transactionId, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
