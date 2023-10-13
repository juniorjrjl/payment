package com.ead.payment.core.port.service;

import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentRequestPaymentDomain;
import com.ead.payment.adapter.dto.PaymentCommandDTO;

import java.util.UUID;

public interface PaymentServicePort {

    void makePayment(final PaymentCommandDTO dto);

    PaymentDomain requestPayment(final PaymentRequestPaymentDomain domain, final UUID userId);

}
