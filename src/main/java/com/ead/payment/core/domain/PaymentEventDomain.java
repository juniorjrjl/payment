package com.ead.payment.core.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PaymentEventDomain(UUID id,
                                 String control,
                                 OffsetDateTime requestDate,
                                 OffsetDateTime completionDate,
                                 OffsetDateTime expirationDate,
                                 String lastDigitCreditCard,
                                 BigDecimal valuePaid,
                                 String message,
                                 boolean recurrence,
                                 UUID userId
) {
        @Builder
        public PaymentEventDomain {}

}
