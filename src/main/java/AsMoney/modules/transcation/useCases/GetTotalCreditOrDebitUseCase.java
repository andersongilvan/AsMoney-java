package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.enums.AmountType;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoudException;
import AsMoney.modules.user.useCases.findById.FindUserByIdUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class GetTotalCreditOrDebitUseCase {

    private final TransactionRepository transactionRepository;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public GetTotalCreditOrDebitUseCase(TransactionRepository transactionRepository, FindUserByIdUseCase findUserByIdUseCase) {
        this.transactionRepository = transactionRepository;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    public BigDecimal execute(UUID userId, AmountType type) {

        User user = findUserByIdUseCase.execute(userId);
        if (user == null) {
            throw new UserNotFoudException();
        }

        return transactionRepository.findByType(user.getId(), type);
    }
}
