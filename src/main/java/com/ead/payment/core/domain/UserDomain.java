package com.ead.payment.core.domain;

import com.ead.payment.adapter.outbound.persistence.entity.PaymentStatus;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record UserDomain(UUID id,
                         String email,
                         String fullName,
                         String userStatus,
                         String userType,
                         String cpf,
                         String phoneNumber,
                         PaymentStatus paymentStatus,
                         OffsetDateTime paymentExpirationDate,
                         OffsetDateTime firstPaymentDate,
                         OffsetDateTime lastPaymentDate,
                         Set<UUID> paymentsIds
) {

    @Builder(toBuilder = true)
    public UserDomain{}

    public boolean isFirstPayment(){
        return Objects.isNull(this.firstPaymentDate);
    }

}
