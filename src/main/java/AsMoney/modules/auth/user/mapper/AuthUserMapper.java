package AsMoney.modules.auth.user.mapper;


import AsMoney.modules.auth.user.AuthUserResponse;
import AsMoney.modules.user.dto.UserResponse;
import AsMoney.modules.user.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthUserMapper {

    public static AuthUserResponse toAuthUserresponse(String token, User user) {

        UserResponse userResponse = UserResponse
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();

        return AuthUserResponse
                .builder()
                .token(token)
                .userResponse(userResponse)
                .build();
    }

}
