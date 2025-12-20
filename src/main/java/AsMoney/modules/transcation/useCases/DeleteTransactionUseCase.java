package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public DeleteTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void execute(UUID transactionId, UUID userId) {

        transactionRepository.deleteById(transactionId, userId);

    }
}
