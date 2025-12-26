package AsMoney.modules.transcation.controllers;


import AsMoney.config.security.jwt.TokenData;
import AsMoney.modules.transcation.dto.DashboardResponse;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.mapper.TransactionMapper;
import AsMoney.modules.transcation.useCases.GetCurrentMonthUseCase;
import AsMoney.modules.transcation.useCases.GetTransactionsBetweenDatesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/asmoney/transaction")
public class Index {

    private final GetCurrentMonthUseCase getCurrentMonth;
    private final GetTransactionsBetweenDatesUseCase getTransactionsBetweenDates;

    @GetMapping("/dashboard")

    @Operation(summary = "User dashboard",
            description = "Return dashboard of current month when not dates" +
                    "When startDate and endDate specified, it return transactions for period."
    )

    @SecurityRequirement(name = "BearerAuth")

    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            oneOf = {
                                    DashboardResponse.class,
                                    TransactionsResponse[].class
                            }
                    )
            )
    )

    @ApiResponse(
            responseCode = "401",
            description = "Token not found"
    )

    @ApiResponse(
            responseCode = "401",
            description = "Invalid or expired token"
    )

    @Parameter(
            description = "Start date of period, format: (yyyy-MM-dd). Optional",
            example = "2025-02-01"
    )

    @Parameter(
            description = "End date of period, format: (yyyy-MM-dd). Optional",
            example = "2025-02-01"
    )

    public ResponseEntity<Object> index(@AuthenticationPrincipal TokenData tokenData,
                                        @RequestParam(required = false) LocalDate startDate,
                                        @RequestParam(required = false) LocalDate endDate) {

        UUID userId = UUID.fromString(tokenData.id());

        if (startDate == null || endDate == null) {
            DashboardResponse dashboardResponse = this.getCurrentMonth.execute(userId);

            return ResponseEntity.ok(dashboardResponse);
        }

        List<Transaction> result = this.getTransactionsBetweenDates.execute(startDate, endDate, userId);

        List<TransactionsResponse> transactionsResponses = result.stream()
                .map(t -> TransactionMapper.toTransactionResponse(t)).toList();

        return ResponseEntity.ok(transactionsResponses);

    }

}
