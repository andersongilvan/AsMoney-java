package AsMoney.modules.auth.user;

import AsMoney.config.security.jwt.TokenService;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoundException;
import AsMoney.modules.user.repository.UserRepository;
import AsMoney.modules.user.useCases.FindByEmailUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUserUseCaseTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository repository;

    @Mock
    TokenService tokenService;

    @InjectMocks
    AuthUserUseCase useCase;


    String email;
    String password;
    User user;
    AuthUserRequest userRequest;

    @BeforeEach
    void setUp() {

        email = "teste@teste.com";
        password = "123456";

        user = new User();

        userRequest = new AuthUserRequest(email, password);

    }

    @Test
    @DisplayName("should not be able auth user wrong e-mail")
    void shouldNotBeAbleAuthUserWithWrongEmail() {

       when(repository.findByEmail(userRequest.email())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> useCase.execute(userRequest));

        verify(repository).findByEmail(userRequest.email());
        verify(tokenService, never()).generateToken(any());
        verify(passwordEncoder, never()).matches(any(), any());

    }

    @Test
    @DisplayName("should not be able auth user wrong password")
    void shouldNotBeAbleAuthUserWithWrongPassword() {

        user.setPassword(userRequest.password());

        when(repository.findByEmail(userRequest.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> useCase.execute(userRequest));

        verify(repository).findByEmail(userRequest.email());
        verify(passwordEncoder).matches(userRequest.password(), user.getPassword());
        verify(tokenService, never()).generateToken(any());

    }
}