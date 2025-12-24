package AsMoney.modules.user.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        String name,
        String email,
        LocalDateTime createdAt
) {
}
