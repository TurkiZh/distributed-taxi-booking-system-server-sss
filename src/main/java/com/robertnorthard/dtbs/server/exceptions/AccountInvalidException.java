/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robertnorthard.dtbs.server.exceptions;

/**
 * AccountInvalidException
 * @author robertnorthard
 */
public class AccountInvalidException extends Exception {
     public AccountInvalidException(String message){
        super(message);
    }
}
