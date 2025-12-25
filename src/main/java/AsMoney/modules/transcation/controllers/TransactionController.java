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
    //private final GetTotalAmountTransactionUseCase getTotalAmount;
    // private final GetTotalCreditOrDebitUseCase getTotalCreditOrDebit;
    private final GetTransactionsBetweenDatesUseCase getTransactionsBetweenDates;
    private final UpdateTransactionUseCase update;
    private final DeleteTransactionUseCase delete;


    @PostMapping
    public ResponseEntity<Object> create(@AuthenticationPrincipal TokenData tokenData,
                                         @Valid @RequestBody TransactionRequestDto requestDto) {

        UUID userId = UUID.fromString(tokenData.id());

        Transaction transaction = TransactionMapper.toTransaction(requestDto, userId);

        Transaction result = this.register.execute(transaction);

        TransactionsResponse transactionsResponse = TransactionMapper.toTransactionResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionsResponse);


    }

    @GetMapping("/dashboard")
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

    @GetMapping("/user")
    public ResponseEntity<Object> getByUser(@AuthenticationPrincipal TokenData tokenData) {

        UUID userId = UUID.fromString(tokenData.id());

        List<Transaction> result = this.findByUser.execute(userId);

        List<TransactionsResponse> transactionsResponses = result.stream()
                .map(t -> TransactionMapper.toTransactionResponse(t)).toList();

        return ResponseEntity.ok(transactionsResponses);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Object> findById(@AuthenticationPrincipal TokenData tokenData,
                                           @PathVariable UUID transactionId) {

        UUID userId = UUID.fromString(tokenData.id());

        Transaction result = this.findById.execute(transactionId, userId);

        return ResponseEntity.ok(TransactionMapper.toTransactionResponse(result));

    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Object> update(@AuthenticationPrincipal TokenData tokenData,
                                         @PathVariable UUID transactionId,
                                         @Valid @RequestBody TransactionRequestDto updated) {

        UUID userId = UUID.fromString(tokenData.id());

        Transaction transaction = TransactionMapper.toTransaction(updated, userId);

        Transaction result = this.update.execute(transactionId, transaction);

        return ResponseEntity.ok(TransactionMapper.toTransactionResponse(result));

    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal TokenData tokenData,
                                       @PathVariable UUID transactionId) {

        UUID userId = UUID.fromString(tokenData.id());

        this.delete.execute(transactionId, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
