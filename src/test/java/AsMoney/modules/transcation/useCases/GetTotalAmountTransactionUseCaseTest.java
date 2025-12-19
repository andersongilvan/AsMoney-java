package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoundException;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Get total amount transactions use-case")
class GetTotalAmountTransactionUseCaseTest {

    @Mock
    TransactionRepository repository;
    @Mock
    GetUserOptionalByIdUseCase getUserOptionalByIdUseCase;

    @InjectMocks
    GetTotalAmountTransactionUseCase useCase;

    User user;
    UUID userId;

    @BeforeEach
    void setUp() {

        userId = UUID.randomUUID();
        user = new User();

        user.setId(userId);

    }

    @Test
    @DisplayName("should be able get total amount transactions a user")
    void shouldBeableGetSumAmountTransactions() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenReturn(Optional.of(user));
        when(repository.sumAmount(user.getId())).thenReturn(BigDecimal.valueOf(1000));

        BigDecimal result = useCase.execute(user.getId());

        Assertions.assertEquals(result, BigDecimal.valueOf(1000));

        verify(getUserOptionalByIdUseCase).execute(userId);
        verify(repository).sumAmount(user.getId());

    }

    @Test
    @DisplayName("should not be able get total amount a transactions if user not found")
    void shouldNotBeAbleGetSumAmountTransactionsUserNotFound() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class,
                () -> useCase.execute(userId));

        verify(getUserOptionalByIdUseCase).execute(userId);
        verify(repository, never()).sumAmount(any());

    }

}