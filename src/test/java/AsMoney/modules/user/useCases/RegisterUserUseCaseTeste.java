package AsMoney.modules.user.useCases;

import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserAlreadyExistsException;
import AsMoney.modules.user.repository.UserRepository;
import AsMoney.modules.user.useCases.register.RegisterUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTeste {

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should not be able register a user with email duplicated")
    public void shouldNotBeAbleRegisterUserWithEmailDuplicated() {
        User user = new User();

        user.setEmail("teste@teste.com");

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class,
                () -> registerUserUseCase.execute(user));

    }

    @Test
    @DisplayName("should be able is password hash")
    public void shouldBeAbleIsPasswordHash() {
        User user = new User();
        user.setEmail("teste@teste.com");
        user.setPassword("123456");

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(user.getPassword()))
                .thenReturn("Password hash");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = registerUserUseCase.execute(user);

        assertNotEquals("123456", savedUser.getPassword());
    }

}
