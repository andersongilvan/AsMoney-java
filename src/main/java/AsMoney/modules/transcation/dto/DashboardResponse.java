package AsMoney.modules.transcation.dto;

import AsMoney.modules.transcation.entiry.Transaction;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record DashboardResponse(
        List<TransactionsResponse> transactionsResponse,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance
) {
}
