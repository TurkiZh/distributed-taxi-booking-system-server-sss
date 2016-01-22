package com.robertnorthard.dtbs.server.exceptions;

/**
 * AccountAlreadyExistsException
 * @author robertnorthard
 */
public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String message){
        super("Account already exists");
    }
}
