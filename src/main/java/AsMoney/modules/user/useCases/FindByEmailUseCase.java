package AsMoney.modules.user.useCases;


import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindByEmailUseCase {

    private final UserRepository userRepository;

    public FindByEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> execute(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }
}
