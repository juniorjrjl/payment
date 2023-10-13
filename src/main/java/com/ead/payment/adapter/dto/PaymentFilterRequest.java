package com.ead.payment.adapter.dto;

import com.ead.payment.adapter.outbound.persistence.entity.PaymentControl;

import java.math.BigDecimal;

public record PaymentFilterRequest(PaymentControl control,
                                   BigDecimal valuePaid,
                                   String lastDigitCreditCard,
                                   String message) {
}
