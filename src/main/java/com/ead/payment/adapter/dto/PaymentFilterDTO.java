package com.ead.payment.adapter.dto;

import com.ead.payment.adapter.outbound.persistence.entity.PaymentControl;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentFilterDTO(PaymentControl control,
                               BigDecimal valuePaid,
                               String lastDigitCreditCard,
                               String message,
                               UUID userId) {
}
