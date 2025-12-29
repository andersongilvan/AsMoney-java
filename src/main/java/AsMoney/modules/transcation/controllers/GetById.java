package AsMoney.modules.transcation.controllers;


import AsMoney.config.security.jwt.TokenData;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.mapper.TransactionMapper;
import AsMoney.modules.transcation.useCases.FindTransactionByIdUseCase;
import AsMoney.swaggerResponse.NotFound;
import AsMoney.swaggerResponse.RequiredToken;
import AsMoney.swaggerResponse.Unauthorized;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@Tag(name = "Transaction")

@RequiredArgsConstructor
@RestController
@RequestMapping("/asmoney/transaction")
public class GetById {

    private final FindTransactionByIdUseCase findById;


    @Operation(summary = "Get transaction by id",
            description =
                    "## Return Flow\n" +
                            "Returns the details of a specific transaction.\n" +
                            "\n**200:** Ok\n" +
                            "\n"+
                            "### Possible Errors:\n\n" +
                            "**401**: Missing or invalid token.\n" +
                            "\n" +
                            "**403**: User does not have permission to access this transaction.\n" +
                            "\n" +
                            "**404**: Transaction ID not found.")

    @SecurityRequirement(name = "bearerAuth")

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = TransactionsResponse.class
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",

                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RequiredToken.class)
                            )
                    ),

                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",

                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Unauthorized.class)
                            )

                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",

                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NotFound.class)
                            )

                    )
            }
    )

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionsResponse> handler(@AuthenticationPrincipal TokenData tokenData, @PathVariable UUID transactionId) {
        UUID userId = UUID.fromString(tokenData.id());

        Transaction result = this.findById.execute(transactionId, userId);

        return ResponseEntity.ok(TransactionMapper.toTransactionResponse(result));
    }

}
