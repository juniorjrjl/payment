package com.ead.payment.core.domain;

import com.ead.payment.adapter.outbound.persistence.entity.PaymentControl;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.EFFECTED;
import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.ERROR;
import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.REFUSED;
import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.REQUESTED;

public record PaymentDomain(
        UUID id,
        PaymentControl control,
        OffsetDateTime requestDate,
        OffsetDateTime completionDate,
        OffsetDateTime expirationDate,
        String lastDigitCreditCard,
        BigDecimal valuePaid,
        String message,
        boolean recurrence
) {

    @Builder(toBuilder = true)
    public PaymentDomain{}

    public boolean isRequested(){
        return control.equals(REQUESTED);
    }

    public boolean isEffected(){
        return control.equals(EFFECTED) && expirationDate.isAfter(OffsetDateTime.now());
    }

    public boolean notifyPaymentEvent(){
        return this.control.equals(EFFECTED) || this.control.equals(REFUSED);
    }

    public boolean isError(){
        return this.control.equals(ERROR);
    }

}
