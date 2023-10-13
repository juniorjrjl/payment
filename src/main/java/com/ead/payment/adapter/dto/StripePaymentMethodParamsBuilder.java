package com.ead.payment.adapter.dto;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class StripePaymentMethodParamsBuilder {

    private final static String TYPE = "type";
    private final static String CARD = "card";

    private final Map<String, Object> params = new HashMap<>();

    public static StripePaymentMethodParamsBuilder builder(){
        return new StripePaymentMethodParamsBuilder();
    }

    private StripePaymentMethodParamsBuilder setProp(final String key, final Object value){
        params.put(key, value);
        return this;
    }

    public StripePaymentMethodParamsBuilder type(final String type){
        return this.setProp(TYPE, type);
    }

    public StripePaymentMethodParamsBuilder card(final Map<String, Object> card){
        return this.setProp(CARD, card);
    }

    public Map<String, Object> build(){
        if (params.containsKey(TYPE)){
            throw new IllegalArgumentException("payment method must have an " + TYPE + " property");
        }
        if (params.containsKey(CARD)){
            throw new IllegalArgumentException("payment method must have a " + CARD + " property");
        }
        return params;
    }

}
