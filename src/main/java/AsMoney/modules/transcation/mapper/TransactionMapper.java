package AsMoney.modules.transcation.mapper;


import AsMoney.modules.transcation.dto.DashboardResponse;
import AsMoney.modules.transcation.dto.TransactionsResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class TransactionMapper {

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

        return DashboardResponse
                .builder()
                .transactionsResponse(transactionsResponse)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(balance)
                .build();


    }

}
