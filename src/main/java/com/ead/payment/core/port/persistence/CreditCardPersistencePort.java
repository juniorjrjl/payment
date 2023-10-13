package com.ead.payment.core.port.persistence;

import com.ead.payment.core.domain.CreditCardDomain;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardPersistencePort {

    Optional<CreditCardDomain> findById(final UUID id);

    Optional<CreditCardDomain> findByUserId(final UUID userId);

    CreditCardDomain save(final CreditCardDomain domain);

}
