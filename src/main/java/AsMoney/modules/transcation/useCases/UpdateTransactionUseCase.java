package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.TransactionNotFoundException;
import AsMoney.modules.transcation.exceptions.UnauthorizedTransactionAccessException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.exceptions.UserNotFoundException;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public UpdateTransactionUseCase(TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }

    public Transaction execute(UUID userId, UUID transactionId, Transaction transactionUpdate) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (!transaction.getUser().getId().equals(userId)) {
            throw new UnauthorizedTransactionAccessException();
        }

        transaction.setAmount(transactionUpdate.getAmount());
        transaction.setType(transactionUpdate.getType());
        transaction.setTitle(transactionUpdate.getTitle());
        transaction.setDescription(transactionUpdate.getDescription());

        return transactionRepository.save(transaction);
    }
}
