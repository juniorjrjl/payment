package com.ead.payment.core.port.persistence;

import com.ead.payment.core.domain.PageInfo;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentFilterDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentPersistencePort {

    Optional<PaymentDomain> findById(final UUID id);

    Optional<PaymentDomain> findLastPaymentByUser(final UUID userId);

    List<PaymentDomain> findAllByUser(final PaymentFilterDomain filter, final PageInfo pageInfo);

    Optional<PaymentDomain> findLastPaymentByUser(final UUID userId, final UUID id);

    PaymentDomain save(final PaymentDomain domain);

}
