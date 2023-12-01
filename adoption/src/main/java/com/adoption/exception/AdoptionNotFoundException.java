package com.adoption.exception;

public class AdoptionNotFoundException extends RuntimeException{

    public AdoptionNotFoundException(String message) {
        super(message);
    }
}