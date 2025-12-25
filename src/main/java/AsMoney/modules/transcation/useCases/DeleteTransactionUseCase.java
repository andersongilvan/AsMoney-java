package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DeleteTransactionUseCase {

    private final TransactionRepository transactionRepository;

    @Transactional
    public void execute(UUID transactionId, UUID userId) {

        transactionRepository.deleteByIdWhereUser(transactionId, userId);

    }
}
