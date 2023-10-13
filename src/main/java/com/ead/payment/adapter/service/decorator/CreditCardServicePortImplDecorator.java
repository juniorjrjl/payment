package com.ead.payment.adapter.service.decorator;

import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.port.service.CreditCardServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CreditCardServicePortImplDecorator implements CreditCardServicePort {

    private final CreditCardServicePort creditCardServicePort;

    @Transactional
    @Override
    public CreditCardDomain save(final CreditCardDomain domain) {
        return creditCardServicePort.save(domain);
    }
}
