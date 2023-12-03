package com.users.exceptions;

public class NoEntityFoundException extends RuntimeException{
    public NoEntityFoundException(String message){
        super(message);
    }
}