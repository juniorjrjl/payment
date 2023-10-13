package com.ead.payment.adapter.outbound.persistence;

import com.ead.payment.adapter.outbound.persistence.entity.CreditCardEntity;
import com.ead.payment.adapter.outbound.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardJpaRepository extends JpaRepository<CreditCardEntity, UUID>, JpaSpecificationExecutor<CreditCardEntity> {

    Optional<CreditCardEntity> findByUserId(final UUID userId);

}
