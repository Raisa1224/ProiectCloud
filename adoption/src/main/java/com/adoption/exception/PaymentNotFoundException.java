package com.adoption.exception;

public class PaymentNotFoundException extends RuntimeException{

    public PaymentNotFoundException(String message) {
        super(message);
    }
}