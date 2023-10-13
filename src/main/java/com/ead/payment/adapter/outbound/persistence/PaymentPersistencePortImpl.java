package com.ead.payment.adapter.outbound.persistence;

import com.ead.payment.adapter.mapper.PaymentMapper;
import com.ead.payment.adapter.spec.PaymentSpec;
import com.ead.payment.core.domain.PageInfo;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentFilterDomain;
import com.ead.payment.core.port.persistence.PaymentPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PaymentPersistencePortImpl implements PaymentPersistencePort {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public Optional<PaymentDomain> findById(final UUID id) {
        return paymentJpaRepository.findById(id).map(paymentMapper::toDomain);
    }

    @Override
    public Optional<PaymentDomain> findLastPaymentByUser(final UUID userId) {
        return paymentJpaRepository.findTopByUserIdOrderByRequestDateDesc(userId).map(paymentMapper::toDomain);
    }

    @Override
    public List<PaymentDomain> findAllByUser(final PaymentFilterDomain filterDomain, final PageInfo pageInfo) {
        var pageable = PageRequest.of(pageInfo.pageNumber(), pageInfo.pageSize());
        var filter = paymentMapper.toFilterDTO(filterDomain);
        return paymentJpaRepository.findAll(PaymentSpec.findAllByUserFilter(filter), pageable)
                .getContent().stream().map(paymentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentDomain> findLastPaymentByUser(final UUID userId, final UUID id) {
        return paymentJpaRepository.findByIdAndUserId(userId, id).map(paymentMapper::toDomain);
    }

    @Override
    public PaymentDomain save(final PaymentDomain domain) {
        var entity = paymentJpaRepository.save(paymentMapper.toEntity(domain));
        return paymentMapper.toDomain(entity);
    }

}
