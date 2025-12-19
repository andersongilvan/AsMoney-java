package AsMoney.modules.user.useCases;


import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoundException;
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

import static org.mockito.Mockito.verify;
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

        Assertions.assertThrows(UserNotFoundException.class,
                () -> useCase.execute(wrongId));
    }

    @Test
    @DisplayName("should be able find a user by id")
    public void shouldBeableFindUserById() {

        var id = UUID.randomUUID();

        User user = new User();
        user.setId(id);

        when(repository.findById(id))
                .thenReturn(Optional.of(user));

        User result = useCase.execute(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());

        verify(repository).findById(id);
    }
}
