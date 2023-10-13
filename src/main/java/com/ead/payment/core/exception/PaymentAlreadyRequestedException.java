package com.ead.payment.core.exception;

public class PaymentAlreadyRequestedException extends PaymentException{

    public PaymentAlreadyRequestedException(final String message) {
        super(message);
    }

}
