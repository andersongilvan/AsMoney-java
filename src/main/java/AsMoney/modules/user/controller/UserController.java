package AsMoney.modules.user.controller;


import AsMoney.modules.user.dto.UserRequest;
import AsMoney.modules.user.dto.UserResponse;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.mapper.UserMapper;
import AsMoney.modules.user.useCases.register.RegisterUserUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/asmoney")
public class UserController {

    private final RegisterUserUseCase register;


    @PostMapping("/login")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRequest request) {

        System.out.println(request);

        User user = UserMapper.toUser(request);

        User result = this.register.execute(user);

        UserResponse response = UserMapper.toUserResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


}
