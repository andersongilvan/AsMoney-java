package AsMoney.modules.transcation.mapper;


import AsMoney.modules.transcation.dto.DashboardResponse;
import AsMoney.modules.transcation.dto.TransactionRequestDto;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class TransactionMapper {

    public static Transaction toTransaction(TransactionRequestDto dto) {
        return Transaction
                .builder()
                .title(dto.title())
                .description(dto.description())
                .amount(dto.amount())
                .type(dto.amountType())
                .build();
    }

    public static TransactionsResponse toTransactionResponse(Transaction transaction) {
        return TransactionsResponse
                .builder()
                .id(transaction.getId())
                .title(transaction.getTitle())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .amountType(transaction.getType())
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    public static DashboardResponse toDashboardResponse(List<Transaction> transactions,
                                                        BigDecimal totalIncome,
                                                        BigDecimal totalExpense,
                                                        BigDecimal balance) {

        List<TransactionsResponse> transactionsResponse = transactions.stream()
                .map(t -> {
                    return TransactionsResponse.builder()
                            .id(t.getId())
                            .title(t.getTitle())
                            .description(t.getDescription())
                            .amount(t.getAmount())
                            .amountType(t.getType())
                            .createdAt(t.getCreatedAt())
                            .build();
                }).toList();

        BigDecimal totalBalance = totalIncome.add(totalExpense);

        return DashboardResponse
                .builder()
                .transactionsResponse(transactionsResponse)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(totalBalance)
                .build();


    }

}
