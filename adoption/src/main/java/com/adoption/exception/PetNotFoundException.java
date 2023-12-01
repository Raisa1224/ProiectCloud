package com.adoption.exception;

public class PetNotFoundException extends RuntimeException{

    public PetNotFoundException(String message) {
        super(message);
    }
}