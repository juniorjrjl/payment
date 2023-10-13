package com.ead.payment.core.domain;

import com.ead.payment.adapter.outbound.persistence.entity.PaymentControl;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentFilterDomain(PaymentControl control,
                                  BigDecimal valuePaid,
                                  String lastDigitCreditCard,
                                  String message,
                                  UUID userId) {
}
