package com.ead.payment.core.service.query;

import com.ead.payment.core.domain.PageInfo;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentFilterDomain;
import com.ead.payment.core.exception.DomainNotFoundException;
import com.ead.payment.core.port.persistence.PaymentPersistencePort;
import com.ead.payment.core.port.service.query.PaymentQueryServicePort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PaymentQueryServicePortImpl implements PaymentQueryServicePort {

    private final PaymentPersistencePort paymentPersistencePort;

    @Override
    public PaymentDomain findById(final UUID id) {
        return paymentPersistencePort.findById(id).orElseThrow(() -> new DomainNotFoundException("Payment not found"));
    }

    @Override
    public PaymentDomain findLastPaymentByUser(final UUID userId) {
        return paymentPersistencePort.findLastPaymentByUser(userId).orElseThrow(() -> new DomainNotFoundException("Payment not found"));
    }

    @Override
    public List<PaymentDomain> findAllByUser(final PaymentFilterDomain filterDomain, final PageInfo pageInfo) {
        return paymentPersistencePort.findAllByUser(filterDomain, pageInfo);
    }

    @Override
    public PaymentDomain findLastPaymentByUser(final UUID userId, final UUID paymentId) {
        return paymentPersistencePort.findLastPaymentByUser(userId, paymentId).orElseThrow(() -> new DomainNotFoundException("Payment not found"));
    }

}
