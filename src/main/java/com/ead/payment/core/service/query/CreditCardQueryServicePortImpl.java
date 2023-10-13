package com.ead.payment.core.service.query;

import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.exception.DomainNotFoundException;
import com.ead.payment.core.port.persistence.CreditCardPersistencePort;
import com.ead.payment.core.port.service.query.CreditCardQueryServicePort;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CreditCardQueryServicePortImpl implements CreditCardQueryServicePort {

    private final CreditCardPersistencePort creditCardPersistencePort;

    @Override
    public CreditCardDomain findById(final UUID id) {
        return creditCardPersistencePort.findById(id).orElseThrow(() -> new DomainNotFoundException("Credit card not found"));
    }

    @Override
    public CreditCardDomain findByUserId(final UUID userId) {
        return creditCardPersistencePort.findByUserId(userId).orElseThrow(() -> new DomainNotFoundException("Credit card not found"));
    }

}
