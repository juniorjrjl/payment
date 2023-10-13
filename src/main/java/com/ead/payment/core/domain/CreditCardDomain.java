package com.ead.payment.core.domain;

import lombok.Builder;

import java.util.UUID;

public record CreditCardDomain(UUID id,
                               String holderFullName,
                               String holderCpf,
                               String creditCardNumber,
                               String expirationDate,
                               String cvvCode,
                               UUID userId
) {

    @Builder(toBuilder = true)
    public CreditCardDomain{}

}
