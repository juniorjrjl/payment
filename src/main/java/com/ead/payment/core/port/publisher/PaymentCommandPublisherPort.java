package com.ead.payment.core.port.publisher;

import com.ead.payment.core.domain.PaymentCommandDomain;

public interface PaymentCommandPublisherPort {
    void publishPaymentCommend(PaymentCommandDomain domain);

}
