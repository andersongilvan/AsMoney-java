package AsMoney.modules.auth.user;


import AsMoney.config.security.jwt.TokenService;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoundException;
import AsMoney.modules.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserUseCase {

    private final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String execute(AuthUserRequest userRequest) {

        User user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(UserNotFoundException::new);

        boolean passwordMatches = passwordEncoder.matches(userRequest.password(), user.getPassword());

        if (!passwordMatches) {
            throw new UserNotFoundException();
        }

        String token = tokenService.generateToken(user);

        return token;

    }
}
