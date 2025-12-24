package AsMoney.modules.user.mapper;


import AsMoney.modules.user.dto.UserRequest;
import AsMoney.modules.user.dto.UserResponse;
import AsMoney.modules.user.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(UserRequest dto) {
        return User
                .builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
