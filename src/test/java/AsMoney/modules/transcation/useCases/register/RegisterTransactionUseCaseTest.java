package AsMoney.modules.transcation.useCases.register;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.enums.AmountType;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.transcation.useCases.RegisterTransactionUseCase;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.useCases.findById.FindUserByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RegisterTransactionUseCaseTest {

    @Mock
    TransactionRepository repository;

    @Mock
    FindUserByIdUseCase findUserByIdUseCase;

    @InjectMocks
    RegisterTransactionUseCase useCase;

    UUID userId;
    User user;
    Transaction transaction;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);

        transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(100.0);

    }

    @Test
    @DisplayName("should be able create a transaction type CREDIT with positive amount")
    void shouldBeAbleCreateCreditTransaction() {

        transaction.setType(AmountType.CREDIT);

        when(findUserByIdUseCase.execute(userId)).thenReturn(user);
        when(repository.save(transaction)).thenReturn(transaction);

        Transaction result = useCase.execute(transaction);

        Assertions.assertEquals(100.0, result.getAmount());
        Assertions.assertEquals(user, result.getUser());

        verify(findUserByIdUseCase).execute(userId);
        verify(repository).save(transaction);

    }

    @Test
    @DisplayName("should be able create transaction type DEBIT with negative amount")
    void shouldBeAbleCreateDebitTransaction() {

        transaction.setType(AmountType.DEBIT);

        when(findUserByIdUseCase.execute(userId)).thenReturn(user);
        when(repository.save(transaction)).thenReturn(transaction);

        Transaction resul = useCase.execute(transaction);

        Assertions.assertEquals(-100.0, resul.getAmount());
        Assertions.assertEquals(user, resul.getUser());

        verify(findUserByIdUseCase).execute(userId);
        verify(repository).save(transaction);

    }
}