package AsMoney.modules.user.useCases;


import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoundException;
import AsMoney.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserOptionalByIdUseCase {

    private final UserRepository userRepository;

    public GetUserOptionalByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> execute(UUID userId) {
        return Optional.of(userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new));
    }
}
