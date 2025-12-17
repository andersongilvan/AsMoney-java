package AsMoney.modules.user.useCases.register;

import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserAlreadyExistsException;
import AsMoney.modules.user.exceptions.UserNotFoudException;
import AsMoney.modules.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User execute(User user) {

        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException();
                });

        String passwordHsh = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordHsh);

        return userRepository.save(user);
    }
}
