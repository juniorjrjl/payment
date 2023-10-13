package com.ead.payment.core.service;

import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.port.persistence.CreditCardPersistencePort;
import com.ead.payment.core.port.service.CreditCardServicePort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreditCardServicePortImpl implements CreditCardServicePort {

    private final CreditCardPersistencePort creditCardPersistencePort;

    @Override
    public CreditCardDomain save(final CreditCardDomain domain) {
        return creditCardPersistencePort.save(domain);
    }

}
