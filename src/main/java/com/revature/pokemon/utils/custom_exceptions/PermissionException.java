package com.revature.pokemon.utils.custom_exceptions;

public class PermissionException extends RuntimeException{
    public PermissionException(String message){
        super(message);
    }
}
