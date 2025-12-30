package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.TransactionNotFoundException;
import AsMoney.modules.transcation.exceptions.UnauthorizedTransactionAccessException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public UpdateTransactionUseCase(TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction execute(UUID transactionId, Transaction transactionUpdate, UUID userId) {

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
