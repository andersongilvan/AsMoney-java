package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.TransactionNotFoundException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindTransactionByIdUseCase {

    private final TransactionRepository transactionRepository;

    public FindTransactionByIdUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction execute(UUID idTransaction) {
        return transactionRepository.findById(idTransaction)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }
}
