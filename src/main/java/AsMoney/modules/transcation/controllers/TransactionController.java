package AsMoney.modules.transcation.controllers;


import AsMoney.config.security.jwt.TokenData;
import AsMoney.modules.transcation.dto.DashboardResponse;
import AsMoney.modules.transcation.dto.TransactionRequestDto;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.mapper.TransactionMapper;
import AsMoney.modules.transcation.useCases.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/asmoney/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final RegisterTransactionUseCase register;
    private final FindTransactionsByUserUseCase findByUser;
    private final FindTransactionByIdUseCase findById;
    private final GetCurrentMonthUseCase getCurrentMonth;
    private final GetTotalAmountTransactionUseCase getTotalAmount;
    private final GetTotalCreditOrDebitUseCase getTotalCreditOrDebit;
    private final GetTransactionsBetweenDatesUseCase getTransactionsBetweenDates;
    private final UpdateTransactionUseCase update;
    private final DeleteTransactionUseCase delete;


    @PostMapping
    public ResponseEntity<Object> create(@AuthenticationPrincipal TokenData tokenData,
                                         @Valid @RequestBody TransactionRequestDto requestDto) {

        String sub = tokenData.id();

        UUID userId = UUID.fromString(sub);

        Transaction transaction = TransactionMapper.toTransaction(requestDto, userId);

        Transaction result = this.register.execute(transaction);

        TransactionsResponse transactionsResponse = TransactionMapper.toTransactionResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionsResponse);


    }

    @GetMapping
    public ResponseEntity<Object> index(@AuthenticationPrincipal TokenData tokenData,
                                        @RequestParam(required = false) LocalDate startDate,
                                        @RequestParam(required = false) LocalDate endDate) {

        String sub = tokenData.id();

        UUID userId = UUID.fromString(sub);

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
