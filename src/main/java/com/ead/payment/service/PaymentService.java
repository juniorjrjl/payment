package com.ead.payment.service;

import com.ead.payment.dto.PaymentRequestDTO;
import com.ead.payment.model.PaymentModel;
import com.ead.payment.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface PaymentService {


    PaymentModel requestPayment(final PaymentRequestDTO request, final UserModel userModel);

    Optional<PaymentModel> findLastPaymentByUser(final UserModel user);

    Page<PaymentModel> findAllByUser(final Specification<PaymentModel> spec, final Pageable pageable);

    Optional<PaymentModel> findLastPaymentByUser(final UUID userId, final UUID paymentId);

}
