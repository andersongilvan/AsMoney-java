package AsMoney.modules.transcation.controllers;

import AsMoney.config.security.jwt.TokenData;
import AsMoney.modules.transcation.dto.TransactionRequestDto;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.mapper.TransactionMapper;
import AsMoney.modules.transcation.useCases.RegisterTransactionUseCase;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Transaction")

@RequiredArgsConstructor
@RestController
@RequestMapping("/asmoney/transaction")
public class Create {

    private final RegisterTransactionUseCase register;


    @Operation(summary = "Create a Transaction of authenticated user",
            description =
                    "## Return Flow\n" +
                            "Create new transaction.\n" +
                            "### Success:" +
                            "\n**201:** Created\n" +
                            "\n" +
                            "### Possible Errors:" +
                            "\n" +
                            "**401**: Missing or invalid token.\n" +
                            "\n" +
                            "**403**: Forbidden")

    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "201",
                    description = "Created",
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

    @PostMapping
    public ResponseEntity<TransactionsResponse> handler(@AuthenticationPrincipal TokenData tokenData,
                                                        @Valid @RequestBody TransactionRequestDto requestDto) {

        UUID userId = UUID.fromString(tokenData.id());

        Transaction transaction = TransactionMapper.toTransaction(requestDto, userId);

        Transaction result = this.register.execute(transaction);

        TransactionsResponse transactionsResponse = TransactionMapper.toTransactionResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionsResponse);


    }

}
