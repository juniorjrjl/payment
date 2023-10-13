package com.ead.payment.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PaymentEventDTO(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("control")
        String control,
        @JsonProperty("requestDate")
        OffsetDateTime requestDate,
        @JsonProperty("completionDate")
        OffsetDateTime completionDate,
        @JsonProperty("expirationDate")
        OffsetDateTime expirationDate,
        @JsonProperty("lastDigitCreditCard")
        String lastDigitCreditCard,
        @JsonProperty("valuePaid")
        BigDecimal valuePaid,
        @JsonProperty("message")
        String message,
        @JsonProperty("recurrence")
        boolean recurrence,
        @JsonProperty("userId")
        UUID userId
) {

    @Builder(toBuilder = true)
    public PaymentEventDTO{}

}
