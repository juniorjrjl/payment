package com.ead.payment.adapter.service;

import com.ead.payment.adapter.mapper.StripeProcessorMapper;
import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.port.service.PaymentProcessorServicePort;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.EFFECTED;
import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.ERROR;
import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.REFUSED;

@Service
@Log4j2
public class StripeProcessorServicePortImpl implements PaymentProcessorServicePort {

    private final String secretKeyStripe;
    private final StripeProcessorMapper stripeProcessorMapper;

    public StripeProcessorServicePortImpl(@Value("${ead.stripe.secret-key}")final String secretKeyStripe,
                                          final StripeProcessorMapper stripeProcessorMapper) {
        this.secretKeyStripe = secretKeyStripe;
        this.stripeProcessorMapper = stripeProcessorMapper;
    }

    @Transactional
    @Override
    public PaymentDomain processStripePayment(final PaymentDomain domain, final CreditCardDomain creditCardDomain) {
        Stripe.apiKey = secretKeyStripe;
        var paymentIntentId = "";
        var paymentDomainBuilder = domain.toBuilder();
        try{
            var paramsPaymentIntent = stripeProcessorMapper.paymentIntentParams(domain);
            var paymentIntent = PaymentIntent.create(paramsPaymentIntent);
            paymentIntentId = paymentIntent.getId();

            var paramsPaymentMethod = stripeProcessorMapper.paymentMethodCardParams(creditCardDomain);
            var paymentMethod = PaymentMethod.create(paramsPaymentMethod);

            Map<String, Object> paramsPaymentConfirm = new HashMap<>();
            paramsPaymentConfirm.put("payment_method", paymentMethod.getId());
            var confirmPaymentIntent = paymentIntent.confirm(paramsPaymentConfirm);


            if (confirmPaymentIntent.getStatus().equals("succeeded")) {
                paymentDomainBuilder.control(EFFECTED);
                paymentDomainBuilder.message("payment effected - paymentIntent: " + paymentIntentId);
                paymentDomainBuilder.completionDate(OffsetDateTime.now());
            } else {
                paymentDomainBuilder.control(ERROR);
                paymentDomainBuilder.message("payment error v1 - paymentIntent: " + paymentIntentId);
            }
        } catch (CardException cardException){
            log.error("A payment error occurred: ", cardException);
            try{
                paymentDomainBuilder.control(REFUSED);
                var paymentIntent = PaymentIntent.retrieve(paymentIntentId);
                var message = """
                        payment refused v1 paymentIntent: %s, cause: %s, message: %s
                        """.formatted(paymentIntentId, paymentIntent.getLastPaymentError().getCode(), paymentIntent.getLastPaymentError().getMessage());
                paymentDomainBuilder.message(message);
            } catch (Exception ex){
                paymentDomainBuilder.message("payment refused v2 - paymentIntent: " + paymentIntentId);
                log.error("Another problem occurred, maybe unrelated to Stripe");
            }
        }catch (Exception ex){
            paymentDomainBuilder.control(ERROR);
            paymentDomainBuilder.message("payment error v2 - paymentIntent: " + paymentIntentId);
            log.error("Another problem occurred, maybe unrelated toStripe.", ex);
        }
        return paymentDomainBuilder.build();
    }
}
