package com.ead.payment.adapter.dto;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class StripePaymentIntentParamsBuilder {

    private final static String AMOUNT = "amount";
    public static final String CURRENCY = "currency";
    public static final String PAYMENT_METHOD_TYPES = "payment_method_types";
    private final Map<String, Object> params = new HashMap<>();



    public static StripePaymentIntentParamsBuilder builder(){
        return new StripePaymentIntentParamsBuilder();
    }

    private StripePaymentIntentParamsBuilder setProp(final String key, final Object value){
        params.put(key, value);
        return this;
    }

    public StripePaymentIntentParamsBuilder amount(final BigDecimal amount){
        return this.setProp(AMOUNT, amount.multiply(new BigDecimal("100")).longValue());
    }

    public StripePaymentIntentParamsBuilder currency(final String currency){
        return this.setProp(CURRENCY, currency);
    }

    public StripePaymentIntentParamsBuilder paymentMethodTypes(final String paymentMethodType){
        List<Object> paymentMethodTypes = List.of(paymentMethodType);
        return this.setProp(PAYMENT_METHOD_TYPES, paymentMethodTypes);
    }

    public Map<String, Object> build(){
        if (params.containsKey(AMOUNT)){
            throw new IllegalArgumentException("payment intent must have an " + AMOUNT + " property");
        }
        if (params.containsKey(CURRENCY)){
            throw new IllegalArgumentException("payment intent must have a " + CURRENCY + " property");
        }
        if (params.containsKey(PAYMENT_METHOD_TYPES)){
            throw new IllegalArgumentException("payment intent must have a " + PAYMENT_METHOD_TYPES + " property");
        }
        return params;
    }

}
