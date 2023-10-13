package com.ead.payment.core.port.service;

import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.domain.PaymentDomain;

public interface PaymentProcessorServicePort {

    PaymentDomain processStripePayment(final PaymentDomain domain, final CreditCardDomain creditCardDomain);

}
