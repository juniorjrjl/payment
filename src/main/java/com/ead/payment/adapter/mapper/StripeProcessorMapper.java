package com.ead.payment.adapter.mapper;

import com.ead.payment.adapter.dto.StripePaymentIntentParamsBuilder;
import com.ead.payment.adapter.dto.StripePaymentMethodCardParamsBuilder;
import com.ead.payment.adapter.dto.StripePaymentMethodParamsBuilder;
import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.domain.PaymentDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Map;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class StripeProcessorMapper {

    public Map<String, Object> paymentIntentParams(final PaymentDomain paymentDomain){
        return mapPaymentIntentParams(StripePaymentIntentParamsBuilder.builder(), paymentDomain).build();
    }

    @Mapping(target = "amount", source = "paymentDomain.valuePaid")
    @Mapping(target = "currency", constant = "brl")
    @Mapping(target = "paymentMethodTypes", constant = "card")
    public abstract StripePaymentIntentParamsBuilder mapPaymentIntentParams(@MappingTarget final StripePaymentIntentParamsBuilder builder,
                                                               final PaymentDomain paymentDomain);

    public Map<String, Object> paymentMethodCardParams(final CreditCardDomain creditCardDomain){
        var card = mapPaymentMethodCardParams(StripePaymentMethodCardParamsBuilder.builder(), creditCardDomain).build();
        return StripePaymentMethodParamsBuilder.builder()
                .type("card")
                .card(card)
                .build();
    }

    @Mapping(target = "number", expression = "java(creditCardDomain.creditCardNumber().replaceAll(\" \", \"\"))")
    @Mapping(target = "cvc", source = "cvvCode")
    @Mapping(target = "expMonth", expression = "java(creditCardDomain.expirationDate().split(\"/\")[0])")
    @Mapping(target = "expYear", expression = "java(creditCardDomain.expirationDate().split(\"/\")[1])")
    public abstract StripePaymentMethodCardParamsBuilder mapPaymentMethodCardParams(@MappingTarget final StripePaymentMethodCardParamsBuilder builder,
                                                                                final CreditCardDomain creditCardDomain);
}
