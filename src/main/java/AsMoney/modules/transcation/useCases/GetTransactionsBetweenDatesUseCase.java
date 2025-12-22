package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.TransactionNotFoundException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GetTransactionsBetweenDatesUseCase {

    private final GetUserOptionalByIdUseCase getUserOptionalByIdUseCase;
    private final TransactionRepository transactionRepository;

    public GetTransactionsBetweenDatesUseCase(GetUserOptionalByIdUseCase getUserOptionalByIdUseCase, TransactionRepository transactionRepository) {
        this.getUserOptionalByIdUseCase = getUserOptionalByIdUseCase;
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> execute(LocalDate startDate, LocalDate endDate, UUID userId) {

        getUserOptionalByIdUseCase.execute(userId);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        List<Transaction> transactions = transactionRepository.findBetweenDates(start, end, userId);

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transaction found for the specified period");
        }

        return transactions;

    }

}
