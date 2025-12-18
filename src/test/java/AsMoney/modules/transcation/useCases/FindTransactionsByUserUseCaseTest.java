package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.useCases.findById.FindUserByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("find transaction by user use-case")
class FindTransactionsByUserUseCaseTest {

    @Mock
    FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    TransactionRepository repository;

    @InjectMocks
    FindTransactionsByUserUseCase useCase;

    UUID userId;
    User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
    }

    @Test
    @DisplayName("should be able find transactions by user")
    void shouldBeableFindTransactionByUser() {

        user.setId(userId);

        when(findUserByIdUseCase.execute(userId))
                .thenReturn(user);

        when(repository.findByUser(userId))
                .thenReturn(List.of(new Transaction()));

        List<Transaction> result = useCase.execute(userId);

        verify(findUserByIdUseCase).execute(userId);
        verify(repository).findByUser(userId);

    }
}