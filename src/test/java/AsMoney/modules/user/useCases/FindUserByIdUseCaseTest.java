package AsMoney.modules.user.useCases;


import AsMoney.modules.user.exceptions.UserNotFoudException;
import AsMoney.modules.user.repository.UserRepository;
import AsMoney.modules.user.useCases.findById.FindUserByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FindUserByIdUseCaseTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    FindUserByIdUseCase useCase;

    @Test
    @DisplayName("should not be able find a user with wrong id")
    public void shouldNotBeableFindUserWithWrongId() {

        var wrongId = UUID.randomUUID();

        when(repository.findById(wrongId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoudException.class,
                () -> useCase.execute(wrongId));
    }
}
