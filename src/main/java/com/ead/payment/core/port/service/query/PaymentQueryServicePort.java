package com.ead.payment.core.port.service.query;

import com.ead.payment.core.domain.PageInfo;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentFilterDomain;

import java.util.List;
import java.util.UUID;

public interface PaymentQueryServicePort {

    PaymentDomain findById(final UUID id);

    PaymentDomain findLastPaymentByUser(final UUID userId);

    List<PaymentDomain> findAllByUser(final PaymentFilterDomain filterDomain, final PageInfo pageInfo);

    PaymentDomain findLastPaymentByUser(final UUID userId, final UUID paymentId);

}
