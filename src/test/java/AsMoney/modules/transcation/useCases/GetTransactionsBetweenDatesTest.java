package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.TransactionNotFoundException;
import AsMoney.modules.transcation.exceptions.UnauthorizedTransactionAccessException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Get transactions between dates test")
class GetTransactionsBetweenDatesTest {

    @Mock
    GetUserOptionalByIdUseCase getUserOptionalByIdUseCase;
    @Mock
    TransactionRepository repository;

    @InjectMocks
    GetTransactionsBetweenDatesUseCase useCase;

    UUID userId;
    User user;
    LocalDate startDate;
    LocalDate endDate;


    @BeforeEach
    void setUp() {

        userId = UUID.randomUUID();
        user = new User();

        startDate = LocalDate.of(2025, 12, 22);
        endDate = LocalDate.of(2025, 12, 23);

    }


    @Test
    @DisplayName("should be able throw UnauthorizedTransactionException if user not exist")
    void shouldBeAbleThrowUnauthorizedTransactionAccessExceptionIfUserNotExist() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UnauthorizedTransactionAccessException.class,
                () -> useCase.execute(startDate, endDate, userId));

        verify(repository, never())
                .findBetweenDates(any(), any(), any());

    }

    @Test
    @DisplayName("should be able convert the parameters of LocalDate to LocalDateTime")
    void shouldBeAbleConvertLocalDateToLocalDateTime() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenReturn(Optional.of(new User()));
        when(repository.findBetweenDates(any(), any(), eq(userId))).thenReturn(List.of(new Transaction()));

        ArgumentCaptor<LocalDateTime> startCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> endCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        useCase.execute(startDate, endDate, userId);

        verify(repository).findBetweenDates(
                startCaptor.capture(),
                endCaptor.capture(),
                eq(userId)
        );

        Assertions.assertEquals(startDate.atStartOfDay(), startCaptor.getValue());
        Assertions.assertEquals(endDate.plusDays(1).atStartOfDay(), endCaptor.getValue());

    }

    @Test
    @DisplayName("should be able throw TransactionNotFoundException if not transaction exist")
    void shouldBeAbleThrowTransactionNotFoundExceptionIdNotTransactionExist() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenReturn(Optional.of(new User()));
        when(repository.findBetweenDates(any(), any(), eq(userId))).thenReturn(List.of());

        Assertions.assertThrows(TransactionNotFoundException.class,
                () -> useCase.execute(startDate, endDate, userId));

        verify(repository).findBetweenDates(any(), any(), eq(userId));
        verify(getUserOptionalByIdUseCase).execute(userId);
    }
}