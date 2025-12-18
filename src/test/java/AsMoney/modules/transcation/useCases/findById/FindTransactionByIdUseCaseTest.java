package AsMoney.modules.transcation.useCases.findById;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.repository.TransactionRepository;
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

import static org.junit.jupiter.api.Assertions.*;
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

        Assertions.assertEquals(idTransaction, result.getId());

        verify(repository).findById(idTransaction);
    }
}