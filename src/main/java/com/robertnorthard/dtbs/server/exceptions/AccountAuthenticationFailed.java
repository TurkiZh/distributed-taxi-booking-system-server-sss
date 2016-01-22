/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robertnorthard.dtbs.server.exceptions;

/**
 * AccountAlreadyExistsException
 * @author robertnorthard
 */
public class AccountAuthenticationFailed extends Exception {
    public AccountAuthenticationFailed(){
        super("Account authentication failed.");
    }
}
