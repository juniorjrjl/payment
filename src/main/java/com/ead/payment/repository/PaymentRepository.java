package com.ead.payment.repository;

import com.ead.payment.model.PaymentModel;
import com.ead.payment.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentModel, UUID>, JpaSpecificationExecutor<PaymentModel> {

    Optional<PaymentModel> findTopByUserOrderByRequestDateDesc(final UserModel user);

    @Query(value = "select * from TB_PAYMENTS where user_id = :userId and id = :id", nativeQuery = true)
    Optional<PaymentModel> findByIdAndUserId(final UUID userId, final UUID id);

}
