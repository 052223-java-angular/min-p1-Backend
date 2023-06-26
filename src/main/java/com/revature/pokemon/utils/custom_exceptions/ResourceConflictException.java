package com.revature.pokemon.utils.custom_exceptions;

public class ResourceConflictException extends RuntimeException{
    public ResourceConflictException(String message){
        super(message);
    }
}
