package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.enums.AmountType;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.useCases.findById.FindUserByIdUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RegisterTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final FindUserByIdUseCase findUserByIdUseCase;

    @Transactional
    public Transaction execute(Transaction transaction, UUID userId) {

        User user = this.findUserByIdUseCase.execute(userId);

        transaction.setAmount(
                transaction.getType().equals(AmountType.CREDIT)
                        ? transaction.getAmount()
                        : transaction.getAmount().negate());

        transaction.setUser(user);

        return this.transactionRepository.save(transaction);
    }
}
