package AsMoney.modules.transcation.useCases;


import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.exceptions.UserNotFoundException;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GetTotalAmountTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final GetUserOptionalByIdUseCase getUserOptionalByIdUseCase;


    public BigDecimal execute(UUID userId) {

         getUserOptionalByIdUseCase.execute(userId)
                .orElseThrow(UserNotFoundException::new);

        return transactionRepository.sumAmount(userId);

    }

}
