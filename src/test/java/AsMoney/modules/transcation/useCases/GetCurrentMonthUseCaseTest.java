package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.UnauthorizedTransactionAccessException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCurrentMonthUseCaseTest {

    @Mock
    GetUserOptionalByIdUseCase getUserOptionalByIdUseCase;

    @Mock
    TransactionRepository repository;

    @InjectMocks
    GetCurrentMonthUseCase useCase;

    UUID userId;


    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("should not be able fetch transactions if user not exist")
    void shouldNotBeAbleFetchTransactionsIfUserNotExist() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenThrow(UnauthorizedTransactionAccessException.class);

        assertThrows(UnauthorizedTransactionAccessException.class,
                () -> useCase.execute(userId));

        verify(repository, never()).findBetweenDates(any(), any(), any());

    }

    @Test
    @DisplayName("should be able fetch transactions from current month")
    void shouldBeAbleFetchTransactionsFromCurrentMonth() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenReturn(Optional.of(new User()));
        when(repository.findBetweenDates(any(), any(), eq(userId))).thenReturn(List.of(new Transaction()));

        useCase.execute(userId);

        verify(getUserOptionalByIdUseCase).execute(userId);
        verify(repository).findBetweenDates(any(), any(), eq(userId));

    }
}