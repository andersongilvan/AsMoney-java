package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.dto.DashboardResponse;
import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.enums.AmountType;
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

import javax.management.DescriptorKey;
import java.math.BigDecimal;
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
    @DisplayName("should be able return DashboardResponse data")
    void shouldBeAbleReturnDashboardResponseData() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenReturn(Optional.of(new User()));

        when(repository.findBetweenDates(any(), any(), eq(userId))).thenReturn(List.of(new Transaction()));

        when(repository.findTotalAmount(eq(AmountType.CREDIT), any(), any(), eq(userId)))
                .thenReturn(BigDecimal.valueOf(100));

        when(repository.findTotalAmount(eq(AmountType.DEBIT), any(), any(), eq(userId)))
                .thenReturn(BigDecimal.valueOf(40));

        when(repository.findByType(eq(userId), eq(AmountType.CREDIT)))
                .thenReturn(BigDecimal.valueOf(500));


        DashboardResponse dashboardResponse = useCase.execute(userId);

        assertNotNull(dashboardResponse);
        assertEquals(BigDecimal.valueOf(100), dashboardResponse.totalIncome());
        assertEquals(BigDecimal.valueOf(40), dashboardResponse.totalExpense());

        verify(getUserOptionalByIdUseCase).execute(userId);
        verify(repository).findBetweenDates(any(), any(), eq(userId));
        verify(repository).findTotalAmount(eq(AmountType.CREDIT), any(), any(), eq(userId));
        verify(repository).findTotalAmount(eq(AmountType.DEBIT), any(), any(), eq(userId));
        verify(repository).findByType(eq(userId), eq(AmountType.CREDIT));

    }

    @Test
    @DisplayName("should throw exception when user not exist")
    void shouldThrowExceptionWhenUserNotExist() {

        when(getUserOptionalByIdUseCase.execute(userId)).thenReturn(Optional.empty());

        assertThrows(UnauthorizedTransactionAccessException.class,
                () -> useCase.execute(userId));

        verify(getUserOptionalByIdUseCase).execute(userId);
        verify(repository, never()).findBetweenDates(any(), any(), any());
        verify(repository, never()).findTotalAmount(any(), any(), any(), any());

    }
}