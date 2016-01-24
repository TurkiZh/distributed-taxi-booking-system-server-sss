package com.robertnorthard.dtbs.server.exceptions;

/**
 * AccountInvalidException
 * @author robertnorthard
 */
public class AccountNotFoundException extends Exception {
     public AccountNotFoundException(){
        super("Account not found.");
    }
}
