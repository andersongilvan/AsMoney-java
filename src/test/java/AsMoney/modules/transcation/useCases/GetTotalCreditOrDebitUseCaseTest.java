package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.enums.AmountType;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoudException;
import AsMoney.modules.user.useCases.findById.FindUserByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Get total CREDIT or DEBIT use-case")
class GetTotalCreditOrDebitUseCaseTest {

    @Mock
    TransactionRepository repository;

    @Mock
    FindUserByIdUseCase findUserByIdUseCase;

    @InjectMocks
    GetTotalCreditOrDebitUseCase useCase;

    UUID userId;
    User user;
    AmountType type;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        type = AmountType.CREDIT;
    }

    @Test
    @DisplayName("should not be able get total amount if user not found")
    void shouldNotBeableGetTotalAmountIfUserNotFound() {

        when(findUserByIdUseCase.execute(userId)).thenReturn(null);

        Assertions.assertThrows(UserNotFoudException.class,
                () -> useCase.execute(userId, type));

        verify(findUserByIdUseCase).execute(userId);
        verify(repository, never()).findByType(any(), any());

    }

    @Test
    @DisplayName("should be able get total amount for credit")
    void shouldBeAbleGetTotalAmountCredit() {

        user.setId(userId);

        when(findUserByIdUseCase.execute(userId)).thenReturn(user);
        when(repository.findByType(user.getId(), type)).thenReturn(BigDecimal.valueOf(10000));

        BigDecimal result = useCase.execute(user.getId(), type);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(result, BigDecimal.valueOf(10000));


        verify(findUserByIdUseCase).execute(userId);
        verify(repository).findByType(user.getId(), type);
    }

    @Test
    @DisplayName("should be able get amount type DEBIT")
    void shouldBeAbleGetAmountTypeDebit() {

        type = AmountType.DEBIT;

        user.setId(userId);

        when(findUserByIdUseCase.execute(userId)).thenReturn(user);
        when(repository.findByType(user.getId(), type)).thenReturn(BigDecimal.valueOf(10000).negate());

        BigDecimal result = useCase.execute(user.getId(), type);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, BigDecimal.valueOf(10000).negate());

        verify(findUserByIdUseCase).execute(userId);
        verify(repository).findByType(user.getId(), type);

    }

}