package com.ead.payment.adapter.outbound.persistence;

import com.ead.payment.adapter.outbound.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID>, JpaSpecificationExecutor<PaymentEntity> {

    Optional<PaymentEntity> findTopByUserIdOrderByRequestDateDesc(final UUID userId);

    Optional<PaymentEntity> findByIdAndUserId(final UUID userId, final UUID id);

}
