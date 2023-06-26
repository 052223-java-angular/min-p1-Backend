package com.revature.pokemon.utils.custom_exceptions;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException(String message){
        super(message);
    }
}
