package AsMoney.modules.transcation.controllers;


import AsMoney.config.security.jwt.TokenData;
import AsMoney.modules.transcation.dto.TransactionRequestDto;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.mapper.TransactionMapper;
import AsMoney.modules.transcation.useCases.UpdateTransactionUseCase;
import AsMoney.swaggerResponse.RequiredToken;
import AsMoney.swaggerResponse.Unauthorized;
import AsMoney.swaggerResponse.ValidationErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(
        name = "Transaction",
        description = "Endpoint responsible for managing user transactions"
)

@RestController
@RequestMapping("/asmoney/transaction")
@RequiredArgsConstructor
public class Update {

    private final UpdateTransactionUseCase update;


    @Operation(summary = "Update a Transaction",
            description =
                    "## Return Flow\n" +
                            "Returns the details of a specific transaction." +
                            "\n" +
                            "### Success:" +
                            "\n**200:** Ok\n" +
                            "\n" +
                            "### Possible Errors:" +
                            "\n" +
                            "**400**: Validation Error.\n" +
                            "\n" +
                            "**401**: Missing or invalid token.\n" +
                            "\n" +
                            "**403**: Forbidden")


    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrors.class))),

            @ApiResponse(responseCode = "401", description = "Unauthenticated user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequiredToken.class))),

            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Unauthorized.class))),
    })


    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionsResponse> handler(@AuthenticationPrincipal TokenData tokenData,
                                                        @PathVariable UUID transactionId,
                                                        @Valid @RequestBody TransactionRequestDto updated) {

        UUID userId = UUID.fromString(tokenData.id());

        Transaction transaction = TransactionMapper.toTransaction(updated, userId);

        Transaction result = this.update.execute(transactionId, transaction);

        return ResponseEntity.ok(TransactionMapper.toTransactionResponse(result));

    }

}
