package com.ead.payment.adapter.service.decorator.query;

import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.port.service.query.CreditCardQueryServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CreditCardQueryServicePortImplDecorator implements CreditCardQueryServicePort {

    private final CreditCardQueryServicePort creditCardQueryServicePort;

    @Transactional
    @Override
    public CreditCardDomain findById(final UUID id) {
        return creditCardQueryServicePort.findById(id);
    }

    @Transactional
    @Override
    public CreditCardDomain findByUserId(final UUID userId) {
        return creditCardQueryServicePort.findByUserId(userId);
    }

}
