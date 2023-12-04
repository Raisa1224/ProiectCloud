package com.pet.exceptions;

public class BreedNotFoundException extends RuntimeException{
    public BreedNotFoundException(String message){
        super(message);
    }
}
