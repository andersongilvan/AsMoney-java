package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoudException;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class GetTotalAmountTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final GetUserOptionalByIdUseCase getUserOptionalByIdUseCase;

    public GetTotalAmountTransactionUseCase(TransactionRepository transactionRepository, GetUserOptionalByIdUseCase getUserOptionalByIdUseCase) {
        this.transactionRepository = transactionRepository;
        this.getUserOptionalByIdUseCase = getUserOptionalByIdUseCase;
    }

    public BigDecimal execute(UUID userId) {

         getUserOptionalByIdUseCase.execute(userId)
                .orElseThrow(UserNotFoudException::new);

        return transactionRepository.sumAmount(userId);

    }

}
