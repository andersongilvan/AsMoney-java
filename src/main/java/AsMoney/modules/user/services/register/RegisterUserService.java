package AsMoney.modules.user.services.register;

import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoudException;
import AsMoney.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {

    private final UserRepository userRepository;

    public RegisterUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(User user) {

        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new UserNotFoudException();
                });

        return userRepository.save(user);
    }
}
