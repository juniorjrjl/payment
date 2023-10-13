package com.ead.payment.adapter.outbound.persistence;

import com.ead.payment.adapter.outbound.persistence.entity.PaymentEntity;
import com.ead.payment.adapter.outbound.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID>, JpaSpecificationExecutor<PaymentEntity> {

    Optional<PaymentEntity> findTopByUserOrderByRequestDateDesc(final UserEntity user);

    @Query(value = "select * from TB_PAYMENTS where user_id = :userId and id = :id", nativeQuery = true)
    Optional<PaymentEntity> findByIdAndUserId(final UUID userId, final UUID id);

}
