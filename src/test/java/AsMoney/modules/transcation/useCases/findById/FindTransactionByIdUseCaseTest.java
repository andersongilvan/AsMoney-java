package AsMoney.modules.transcation.useCases.findById;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.exceptions.TransactionNotFoundException;
import AsMoney.modules.transcation.repository.TransactionRepository;
import AsMoney.modules.transcation.useCases.FindTransactionByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindTransactionByIdUseCaseTest {

    @Mock
    TransactionRepository repository;

    @InjectMocks
    FindTransactionByIdUseCase useCase;

    UUID idTransaction;
    Transaction transaction;

    @BeforeEach
    void setUp() {
        idTransaction = UUID.randomUUID();
        transaction = new Transaction();
    }

    @Test
    @DisplayName("should be able find a transaction by id")
    void shouldBeableTransactionById() {

        transaction.setId(idTransaction);

        when(repository.findById(idTransaction)).thenReturn(Optional.of(transaction));

        Transaction result = useCase.execute(idTransaction);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(idTransaction, result.getId());

        verify(repository).findById(idTransaction);
    }

    @Test
    @DisplayName("should not be able find a transaction with wrong id")
    void shouldNotBeableFindTransactionWithWrongId() {

        when(repository.findById(idTransaction)).thenReturn(Optional.empty());
        
        Assertions.assertThrows(TransactionNotFoundException.class,
                () -> useCase.execute(idTransaction));

        verify(repository).findById(idTransaction);

    }
}