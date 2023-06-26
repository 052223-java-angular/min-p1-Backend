package com.revature.pokemon.utils.custom_exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message){
        super(message);
    }
}