package com.ead.payment.core.domain;

import lombok.Builder;

import java.util.UUID;

public record PaymentCommandDomain(UUID userId,
                                   UUID paymentId,
                                   UUID cardId) {

    @Builder(toBuilder = true)
    public PaymentCommandDomain {
    }
}
