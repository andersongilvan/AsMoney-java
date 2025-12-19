package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserAlreadyExistsException;
import AsMoney.modules.user.useCases.findById.FindUserByIdUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FindTransactionsByUserUseCase {

    private final TransactionRepository transactionRepository;

    private final FindUserByIdUseCase findUserByIdUseCase;

    public FindTransactionsByUserUseCase(TransactionRepository transactionRepository, FindUserByIdUseCase findUserByIdUseCase) {
        this.transactionRepository = transactionRepository;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }



    public List<Transaction> execute(UUID userId) {

        User user = findUserByIdUseCase.execute(userId);
          if (user == null) {
              throw new UserAlreadyExistsException();
          }

          return transactionRepository.findByUser(userId);
    }

}
