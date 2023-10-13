package com.ead.payment.adapter.service.decorator;

import com.ead.payment.adapter.dto.PaymentCommandDTO;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentRequestPaymentDomain;
import com.ead.payment.core.port.service.PaymentServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentServicePortImplDecorator implements PaymentServicePort {

    private final PaymentServicePort paymentServicePort;

    @Transactional
    @Override
    public void makePayment(final PaymentCommandDTO dto) {
        paymentServicePort.makePayment(dto);
    }

    @Transactional
    @Override
    public PaymentDomain requestPayment(final PaymentRequestPaymentDomain domain, final UUID userId) {
        return paymentServicePort.requestPayment(domain, userId);
    }

}
