package com.ead.payment.core.domain;

import lombok.Builder;

import java.math.BigDecimal;

public record PaymentRequestPaymentDomain(BigDecimal valuePaid,
                                          String holderFullName,
                                          String holderCpf,
                                          String creditCardNumber,
                                          String expirationDate,
                                          String cvvCode) {

    @Builder(toBuilder = true)
    public PaymentRequestPaymentDomain{}

}
