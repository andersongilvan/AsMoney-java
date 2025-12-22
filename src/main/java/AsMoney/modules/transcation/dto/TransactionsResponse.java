package AsMoney.modules.transcation.dto;


import AsMoney.modules.transcation.enums.AmountType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TransactionsResponse(
        UUID id,
        String title,
        String description,
        BigDecimal amount,
        AmountType amountType,
        LocalDateTime createdAt
) {
}
