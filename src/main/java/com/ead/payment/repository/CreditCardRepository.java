package com.ead.payment.repository;

import com.ead.payment.model.CreditCardModel;
import com.ead.payment.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCardModel, UUID>, JpaSpecificationExecutor<CreditCardModel> {

    Optional<CreditCardModel> findByUser(final UserModel userModel);

}
