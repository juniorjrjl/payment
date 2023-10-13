package com.ead.payment.adapter.outbound.persistence;

import com.ead.payment.adapter.mapper.CreditCardMapper;
import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.port.persistence.CreditCardPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CreditCardPersistencePortImpl implements CreditCardPersistencePort {

    private final CreditCardJpaRepository creditCardJpaRepository;
    private final CreditCardMapper creditCardMapper;

    @Override
    public Optional<CreditCardDomain> findById(final UUID id) {
        return creditCardJpaRepository.findById(id).map(creditCardMapper::toDomain);
    }

    @Override
    public Optional<CreditCardDomain> findByUserId(final UUID userId) {
        return creditCardJpaRepository.findByUserId(userId).map(creditCardMapper::toDomain);
    }

    @Override
    public CreditCardDomain save(final CreditCardDomain domain) {
        var entity = creditCardJpaRepository.save(creditCardMapper.toDomain(domain));
        return creditCardMapper.toDomain(entity);
    }

}
