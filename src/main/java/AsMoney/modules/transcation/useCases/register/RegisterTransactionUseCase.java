package AsMoney.modules.transcation.useCases.register;


import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.enums.AmountType;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.useCases.findById.FindUserByIdUseCase;
import org.springframework.stereotype.Service;


@Service
public class RegisterTransactionUseCase {

    private final TransactionRepository transactionRepository;

    private final FindUserByIdUseCase findUserByIdUseCase;

    public RegisterTransactionUseCase(TransactionRepository transactionRepository, FindUserByIdUseCase findUserByIdUseCase) {
        this.transactionRepository = transactionRepository;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }


    public Transaction execute(Transaction transaction) {

        User user = findUserByIdUseCase.execute(transaction.getUser().getId());

        transaction.setAmount(transaction.getType().equals(AmountType.CREDIT) ? transaction.getAmount()
                : transaction.getAmount() * (-1));

        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }
}
