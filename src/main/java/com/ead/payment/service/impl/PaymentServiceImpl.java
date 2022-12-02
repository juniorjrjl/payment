package com.ead.payment.service.impl;

import com.ead.payment.dto.PaymentRequestDTO;
import com.ead.payment.mapper.CreditCardMapper;
import com.ead.payment.mapper.PaymentMapper;
import com.ead.payment.model.CreditCardModel;
import com.ead.payment.model.PaymentModel;
import com.ead.payment.model.UserModel;
import com.ead.payment.repository.CreditCardRepository;
import com.ead.payment.repository.PaymentRepository;
import com.ead.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final CreditCardRepository creditCardRepository;
    private final PaymentMapper paymentMapper;
    private final CreditCardMapper creditCardMapper;

    @Transactional
    @Override
    public PaymentModel requestPayment(final PaymentRequestDTO request, final UserModel userModel) {
        var creditCardModel = new CreditCardModel();
        var storedCreditCard = creditCardRepository.findByUser(userModel);
        creditCardModel = storedCreditCard.orElseGet(() -> creditCardMapper.toModel(request, userModel));
        creditCardRepository.save(creditCardModel);
        var model = paymentMapper.toModel(request, userModel);
        model = paymentRepository.save(model);
        return model;
    }

    @Override
    public Optional<PaymentModel> findLastPaymentByUser(final UserModel user) {
        return paymentRepository.findTopByUserOrderByRequestDateDesc(user);
    }

    @Override
    public Page<PaymentModel> findAllByUser(final Specification<PaymentModel> spec, final Pageable pageable) {
        return paymentRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<PaymentModel> findLastPaymentByUser(final UUID userId, final UUID paymentId) {
        return paymentRepository.findByIdAndUserId(userId, paymentId);
    }

}
