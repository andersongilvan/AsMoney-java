package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteTransactionUseCaseTest {

    @Mock
    TransactionRepository repository;

    @InjectMocks
    DeleteTransactionUseCase useCase;



    private UUID transactionId;
    private UUID userId;

    @BeforeEach
    void setup() {
        transactionId = UUID.randomUUID();
        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("should call repository deleteById with correct params")
    void shouldBeAbleCallRepositoryDeleteById() {

        useCase.execute(userId, transactionId);

        verify(repository).deleteById(userId, transactionId);

    }

}