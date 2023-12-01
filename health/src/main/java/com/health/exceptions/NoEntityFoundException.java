package com.health.exceptions;

public class NoEntityFoundException extends RuntimeException{
    public NoEntityFoundException(String message){
        super(message);
    }
}