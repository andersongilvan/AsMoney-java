package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.dto.DashboardResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.enums.AmountType;
import AsMoney.modules.transcation.exceptions.UnauthorizedTransactionAccessException;
import AsMoney.modules.transcation.mapper.TransactionMapper;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
public class GetCurrentMonthUseCase {

    private final TransactionRepository transactionRepository;
    private final GetUserOptionalByIdUseCase getUserOptionalByIdUseCase;

    public GetCurrentMonthUseCase(TransactionRepository transactionRepository, GetUserOptionalByIdUseCase getUserOptionalByIdUseCase) {
        this.transactionRepository = transactionRepository;
        this.getUserOptionalByIdUseCase = getUserOptionalByIdUseCase;
    }

    public DashboardResponse execute(UUID userId) {

        getUserOptionalByIdUseCase.execute(userId)
                .orElseThrow(UnauthorizedTransactionAccessException::new);

        YearMonth currentMonth = YearMonth.now();

        LocalDateTime start = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime end = currentMonth.plusMonths(1).atDay(1).atStartOfDay();

        List<Transaction> transactions = transactionRepository.findBetweenDates(start, end, userId);

        BigDecimal totalAmountCredit = transactionRepository.findTotalAmount(AmountType.CREDIT, start, end, userId);
        BigDecimal totalAmountDebit = transactionRepository.findTotalAmount(AmountType.DEBIT, start, end, userId);
        BigDecimal totalBalance = transactionRepository.findByType(userId, AmountType.CREDIT);

        return TransactionMapper.toDashboardResponse(transactions, totalAmountCredit, totalAmountDebit, totalBalance);

    }
}
