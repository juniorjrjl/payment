package com.ead.payment.core.exception;

public class DomainNotFoundException extends PaymentException{
    public DomainNotFoundException(final String message) {
        super(message);
    }
}
