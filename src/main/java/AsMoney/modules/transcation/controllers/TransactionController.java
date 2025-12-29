package AsMoney.modules.transcation.controllers;


import AsMoney.config.security.jwt.TokenData;
import AsMoney.modules.transcation.dto.DashboardResponse;
import AsMoney.modules.transcation.dto.TransactionRequestDto;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.mapper.TransactionMapper;
import AsMoney.modules.transcation.useCases.*;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/asmoney/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final FindTransactionByIdUseCase findById;


    private final UpdateTransactionUseCase update;





    @PutMapping("/{transactionId}")
    public ResponseEntity<Object> update(@AuthenticationPrincipal TokenData tokenData,
                                         @PathVariable UUID transactionId,
                                         @Valid @RequestBody TransactionRequestDto updated) {

        UUID userId = UUID.fromString(tokenData.id());

        Transaction transaction = TransactionMapper.toTransaction(updated, userId);

        Transaction result = this.update.execute(transactionId, transaction);

        return ResponseEntity.ok(TransactionMapper.toTransactionResponse(result));

    }



}
