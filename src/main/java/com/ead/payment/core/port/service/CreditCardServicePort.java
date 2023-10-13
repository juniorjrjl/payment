package com.ead.payment.core.port.service;

import com.ead.payment.core.domain.CreditCardDomain;

public interface CreditCardServicePort {

    CreditCardDomain save(final CreditCardDomain domain);

}
