package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.TransactionNotFoundException;
import AsMoney.modules.transcation.exceptions.UnauthorizedTransactionAccessException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UpdateTransactionUseCaseTest {

    @Mock
    TransactionRepository repository;

    @InjectMocks
    UpdateTransactionUseCase useCase;

    User user;
    UUID userId;
    UUID transactionId;
    Transaction transaction;

    @BeforeEach
    void setUp() {

        userId = UUID.randomUUID();
        transactionId = UUID.randomUUID();

        user = new User();
        user.setId(userId);

        transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setUser(user);
        transaction.setTitle("Old title");

    }

    @Test
    @DisplayName("should be able update transaction")
    void shouldBeAbleUpdateTransaction() {


        when(repository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(repository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction updateData = new Transaction();
        updateData.setTitle("Title updated");

        Transaction result = useCase.execute(transactionId, updateData, userId);

        Assertions.assertEquals("Title updated", result.getTitle());

        verify(repository).findById(transactionId);
        verify(repository).save(transaction);

    }

    @Test
    @DisplayName("should not be able update a transaction with id not found")
    void shouldNotBeAbleUpdateTransactionWithIdNotFound() {

        when(repository.findById(transactionId)).thenReturn(Optional.empty());

        Assertions.assertThrows(TransactionNotFoundException.class,
                () -> useCase.execute(transactionId, transaction, userId));

        verify(repository).findById(transactionId);
        verify(repository, never()).save(any());

    }

    @Test
    @DisplayName("should not be able update a transaction with user unauthorized")
    void shouldNorBeAbleUpdateTransactionWithUserUnauthorized() {

        UUID userWrongId = UUID.randomUUID();

        when(repository.findById(transactionId)).thenReturn(Optional.of(transaction));

        Assertions.assertThrows(UnauthorizedTransactionAccessException.class,
                () -> useCase.execute(transactionId, transaction, userWrongId));

        verify(repository).findById(transactionId);
        verify(repository, never()).save(any());

    }
}