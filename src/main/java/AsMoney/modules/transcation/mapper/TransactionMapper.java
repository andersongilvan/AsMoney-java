package AsMoney.modules.transcation.mapper;


import AsMoney.modules.transcation.dto.DashboardResponse;
import AsMoney.modules.transcation.dto.TransactionRequestDto;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.user.entity.User;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class TransactionMapper {

    public static Transaction toTransaction(TransactionRequestDto dto, UUID userId) {
        return Transaction
                .builder()
                .title(dto.title())
                .description(dto.description())
                .amount(dto.amount())
                .type(dto.amountType())
                .user(User.builder().id(userId).build())
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

        System.out.println(totalIncome);
        System.out.println(totalExpense);

        System.out.println("TOTAL BALANCE " + totalBalance);

        return DashboardResponse
                .builder()
                .transactionsResponse(transactionsResponse)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(totalBalance)
                .build();


    }

}
