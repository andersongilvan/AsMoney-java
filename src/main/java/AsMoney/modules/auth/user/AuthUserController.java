package AsMoney.modules.auth.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/asmoney")
public class AuthUserController {

    private final AuthUserUseCase authUserUseCase;


    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthUserRequest request) {

        AuthUserResponse result = this.authUserUseCase.execute(request);

        return ResponseEntity.ok(result);
    }
}
