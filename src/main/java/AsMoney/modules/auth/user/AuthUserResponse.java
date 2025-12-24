package AsMoney.modules.auth.user;

import AsMoney.modules.user.dto.UserResponse;
import AsMoney.modules.user.entity.User;
import lombok.Builder;

@Builder
public record AuthUserResponse(String token, UserResponse userResponse) {
}
