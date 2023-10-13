package com.ead.payment.core.exception;

public class PaymentAlreadyMadeException extends PaymentException{

    public PaymentAlreadyMadeException(final String message) {
        super(message);
    }

}
