package AsMoney.modules.auth.user;


import AsMoney.modules.user.dto.UserRequest;
import AsMoney.swaggerResponse.AlreadyExist;
import AsMoney.swaggerResponse.EmailOrPasswordInvalid;
import AsMoney.swaggerResponse.ValidationErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(
        name = "Auth user Controller",
        description = "Endpoint for authenticate a user"
)


@RequiredArgsConstructor
@RestController
@RequestMapping("/asmoney")
public class AuthUserController {

    private final AuthUserUseCase authUserUseCase;


    @Operation(summary = "Authenticate a user",
            description =
                    "## Return Flow\n" +
                            "Authenticate user.\n" +
                            "### Success:" +
                            "\n**200:** Ok\n" +
                            "\n" +
                            "### Possible Errors:" +
                            "\n" +
                            "**400**: Invalid data." +
                            "\n\n" +
                            "**400**: E-mail or password invalid"
    )

    @ApiResponses(value = {

            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthUserResponse.class))),

            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrors.class))),

            @ApiResponse(responseCode = "400", description = "E-mail or password invalid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailOrPasswordInvalid.class))),
    })

    @PostMapping("/auth")
    public ResponseEntity<AuthUserResponse> handler(@Valid @RequestBody AuthUserRequest request) {

        AuthUserResponse result = this.authUserUseCase.execute(request);

        return ResponseEntity.ok(result);
    }
}
