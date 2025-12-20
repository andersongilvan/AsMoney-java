package AsMoney.modules.transcation.useCases;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.useCases.GetUserOptionalByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.ser.impl.UnknownSerializer;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UpdateTransactionUseCaseTest {

    @Mock
    TransactionRepository repository;

    @Mock
    GetUserOptionalByIdUseCase getUserOptionalByIdUseCase;


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
    void execute() {


        when(repository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(repository.save(any(Transaction.class))).thenReturn(transaction);

      Transaction updateData = new Transaction();
        updateData.setTitle("Title updated");

        Transaction result = useCase.execute(userId, transactionId, updateData);

        Assertions.assertEquals("Title updated", result.getTitle());

        verify(repository).findById(transactionId);
        verify(repository).save(transaction);

    }
}