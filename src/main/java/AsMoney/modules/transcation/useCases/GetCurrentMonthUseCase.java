package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.springframework.stereotype.Service;

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

    public List<Transaction> execute(UUID userId) {

        getUserOptionalByIdUseCase.execute(userId);

        YearMonth currentMonth = YearMonth.now();

        LocalDateTime start = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime end = currentMonth.plusMonths(1).atDay(1).atStartOfDay();

        return transactionRepository.findBetweenDates(start, end, userId);

    }
}
