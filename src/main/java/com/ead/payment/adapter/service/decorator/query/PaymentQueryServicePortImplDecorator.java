package com.ead.payment.adapter.service.decorator.query;

import com.ead.payment.core.domain.PageInfo;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentFilterDomain;
import com.ead.payment.core.port.service.query.PaymentQueryServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentQueryServicePortImplDecorator implements PaymentQueryServicePort {

    private final PaymentQueryServicePort paymentQueryServicePort;

    @Transactional
    @Override
    public PaymentDomain findById(final UUID id) {
        return paymentQueryServicePort.findById(id);
    }

    @Transactional
    @Override
    public PaymentDomain findLastPaymentByUser(final UUID userId) {
        return paymentQueryServicePort.findLastPaymentByUser(userId);
    }

    @Transactional
    @Override
    public List<PaymentDomain> findAllByUser(final PaymentFilterDomain filterDomain, final PageInfo pageInfo) {
        return paymentQueryServicePort.findAllByUser(filterDomain, pageInfo);
    }

    @Transactional
    @Override
    public PaymentDomain findLastPaymentByUser(final UUID userId, final UUID paymentId) {
        return paymentQueryServicePort.findLastPaymentByUser(userId, paymentId);
    }

}
