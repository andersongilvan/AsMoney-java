package AsMoney.modules.user.controller;


import AsMoney.modules.user.dto.UserRequest;
import AsMoney.modules.user.dto.UserResponse;
import AsMoney.modules.user.entity.User;
import AsMoney.modules.user.mapper.UserMapper;
import AsMoney.modules.user.useCases.register.RegisterUserUseCase;
import AsMoney.swaggerResponse.AlreadyExist;
import AsMoney.swaggerResponse.ValidationErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "User Controller",
        description = "Endpoint responsible for managing users"
)

@RequiredArgsConstructor
@RestController
@RequestMapping("/asmoney")
public class UserCreateController {

    private final RegisterUserUseCase register;

    @Operation(summary = "Register a user",
            description =
                    "## Return Flow\n" +
                            "Create new user.\n" +
                            "### Success:" +
                            "\n**201:** Created\n" +
                            "\n" +
                            "### Possible Errors:" +
                            "\n" +
                            "**400**: Invalid data.\n" +
                            "\n" +
                            "**400**: E-mail already exist.\n")

    @ApiResponses(value = {

            @ApiResponse(responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class))),

            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrors.class))),

            @ApiResponse(responseCode = "400", description = "E-mail already esist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlreadyExist.class))),
    })

    @PostMapping("/login")
    public ResponseEntity<UserResponse> handler(@Valid @RequestBody UserRequest request) {

        System.out.println(request);

        User user = UserMapper.toUser(request);

        User result = this.register.execute(user);

        UserResponse response = UserMapper.toUserResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


}
