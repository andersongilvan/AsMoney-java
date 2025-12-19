package AsMoney.modules.user.useCases.findById;


import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.exceptions.UserNotFoundException;
import AsMoney.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindUserByIdUseCase {

    private final UserRepository userRepository;

    public FindUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);
    }
}
