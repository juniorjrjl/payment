package com.ead.payment.core.port.service.query;

import com.ead.payment.core.domain.CreditCardDomain;

import java.util.UUID;

public interface CreditCardQueryServicePort {

    CreditCardDomain findById(final UUID id);

    CreditCardDomain findByUserId(final UUID userId);

}
