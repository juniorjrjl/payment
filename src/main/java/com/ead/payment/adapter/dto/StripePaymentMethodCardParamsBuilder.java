package com.ead.payment.adapter.dto;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class StripePaymentMethodCardParamsBuilder {

    private final static String NUMBER = "number";
    private final static String EXP_MONTH = "exp_month";
    private final static String EXP_YEAR = "exp_year";
    private final static String CVC = "cvc";

    private final Map<String, Object> params = new HashMap<>();

    public static StripePaymentMethodCardParamsBuilder builder(){
        return new StripePaymentMethodCardParamsBuilder();
    }

    private StripePaymentMethodCardParamsBuilder setProp(final String key, final Object value){
        params.put(key, value);
        return this;
    }

    public StripePaymentMethodCardParamsBuilder number(final String number){
        return this.setProp(NUMBER, number);
    }

    public StripePaymentMethodCardParamsBuilder expMonth(final String expMonth){
        return this.setProp(EXP_MONTH, expMonth);
    }

    public StripePaymentMethodCardParamsBuilder expYear(final String expYear){
        return this.setProp(EXP_YEAR, expYear);
    }

    public StripePaymentMethodCardParamsBuilder cvc(final String cvc){
        return this.setProp(CVC, cvc);
    }

    public Map<String, Object> build(){
        if (params.containsKey(NUMBER)){
            throw new IllegalArgumentException("payment method card must have an " + NUMBER + " property");
        }
        if (params.containsKey(EXP_MONTH)){
            throw new IllegalArgumentException("payment method card must have a " + EXP_MONTH + " property");
        }
        if (params.containsKey(EXP_YEAR)){
            throw new IllegalArgumentException("payment method card must have a " + EXP_YEAR + " property");
        }
        if (params.containsKey(CVC)){
            throw new IllegalArgumentException("payment method card must have a " + CVC + " property");
        }
        return params;
    }

}
