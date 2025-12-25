package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.TransactionNotFoundException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FindTransactionByIdUseCase {

    private final TransactionRepository transactionRepository;


    public Transaction execute(UUID idTransaction, UUID userId) {
        return transactionRepository.findByIdAndUserId(idTransaction, userId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }
}
