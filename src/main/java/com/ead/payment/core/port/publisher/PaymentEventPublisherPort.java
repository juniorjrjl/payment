package com.ead.payment.core.port.publisher;

import com.ead.payment.core.domain.PaymentEventDomain;

public interface PaymentEventPublisherPort {

    void publishPaymentEvent(final PaymentEventDomain domain);

}
