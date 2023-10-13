package com.ead.payment.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;
public record PaymentCommandDTO(@JsonProperty("userId")
                                UUID userId,
                                @JsonProperty("paymentId")
                                UUID paymentId,
                                @JsonProperty("cardId")
                                UUID cardId) {

    @Builder(toBuilder = true)
    public PaymentCommandDTO {
    }
}
