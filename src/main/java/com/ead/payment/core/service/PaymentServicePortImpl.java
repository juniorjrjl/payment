package com.ead.payment.core.service;

import com.ead.payment.adapter.mapper.CreditCardMapper;
import com.ead.payment.adapter.mapper.PaymentMapper;
import com.ead.payment.adapter.mapper.UserMapper;
import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentRequestPaymentDomain;
import com.ead.payment.core.exception.DomainNotFoundException;
import com.ead.payment.core.exception.PaymentAlreadyMadeException;
import com.ead.payment.core.exception.PaymentAlreadyRequestedException;
import com.ead.payment.core.port.persistence.PaymentPersistencePort;
import com.ead.payment.core.port.publisher.PaymentCommandPublisherPort;
import com.ead.payment.core.port.publisher.PaymentEventPublisherPort;
import com.ead.payment.core.port.service.CreditCardServicePort;
import com.ead.payment.core.port.service.PaymentProcessorServicePort;
import com.ead.payment.core.port.service.PaymentServicePort;
import com.ead.payment.core.port.service.UserServicePort;
import com.ead.payment.core.port.service.query.CreditCardQueryServicePort;
import com.ead.payment.core.port.service.query.PaymentQueryServicePort;
import com.ead.payment.core.port.service.query.UserQueryServicePort;
import com.ead.payment.adapter.dto.PaymentCommandDTO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

import static com.ead.payment.adapter.outbound.persistence.entity.PaymentStatus.DEBTOR;

@Log4j2
@AllArgsConstructor
public class PaymentServicePortImpl implements PaymentServicePort {

    private final PaymentPersistencePort paymentPersistencePort;
    private final PaymentQueryServicePort paymentQueryServicePort;
    private final PaymentMapper paymentMapper;
    private final PaymentCommandPublisherPort paymentCommandPublisherPort;
    private final PaymentEventPublisherPort paymentEventPublisherPort;
    private final PaymentProcessorServicePort paymentProcessorServicePort;
    private final CreditCardServicePort creditCardServicePort;
    private final CreditCardQueryServicePort creditCardQueryServicePort;
    private final CreditCardMapper creditCardMapper;
    private final UserServicePort userServicePort;
    private final UserQueryServicePort userQueryServicePort;
    private final UserMapper userMapper;

    @Override
    public void makePayment(final PaymentCommandDTO dto) {
        var userDomain = userQueryServicePort.findById(dto.userId());
        var creditCardDomain = creditCardQueryServicePort.findById(dto.cardId());
        var domain = paymentQueryServicePort.findById(dto.paymentId());
        domain = paymentProcessorServicePort.processStripePayment(domain, creditCardDomain);
        paymentPersistencePort.save(domain);
        if (domain.isEffected()){
            userDomain = userMapper.toPaymentEffected(userDomain);
        }else {
            userDomain = userDomain.toBuilder().paymentStatus(DEBTOR).build();
        }
        userServicePort.save(userDomain);
        if (domain.notifyPaymentEvent()){
            paymentEventPublisherPort.publishPaymentEvent(paymentMapper.toEvent(domain, userDomain.id()));
        } else if (domain.isError()) {

        }
    }

    @Override
    public PaymentDomain requestPayment(final PaymentRequestPaymentDomain requestPayment, final UUID userId) {
        userQueryServicePort.findById(userId);
        try {
            var domain = paymentQueryServicePort.findLastPaymentByUser(userId);
            if (domain.isRequested()){
                throw new PaymentAlreadyRequestedException("Payment already requested.");
            }
            if (domain.isEffected()){
                throw new PaymentAlreadyMadeException("Payment already made.");
            }
        }catch (DomainNotFoundException ex){
            log.info("User {} has no payment, starting request", userId);
        }
        CreditCardDomain creditCardDomain;
        try {
            creditCardDomain = creditCardQueryServicePort.findByUserId(userId);
        }catch (DomainNotFoundException ex){
            creditCardDomain = creditCardMapper.toDomain(requestPayment, userId);
        }
        creditCardDomain = creditCardServicePort.save(creditCardDomain);
        var domain = paymentMapper.toDomain(requestPayment, userId);
        domain = paymentPersistencePort.save(domain);
        try{
            var dto = paymentMapper.toCommand(userId, domain.id(), creditCardDomain.id());
            paymentCommandPublisherPort.publishPaymentCommend(dto);
        }catch (Exception ex){
            log.warn("Error sending payment command");
        }
        return domain;
    }

}
