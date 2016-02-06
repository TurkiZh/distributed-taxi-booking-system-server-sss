/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robertnorthard.dtbs.server.layer.model;

import java.io.Serializable;

/**
 *
 * @author robertnorthard
 */
public enum AccountType implements Serializable {
    PASSENGER("passenger"),
    DRIVER("driver"),
    ADMIN("admin");
    
    String roleName;
    
    /**
     * @param roleName role name.
     */
    AccountType(String roleName){
        this.roleName = roleName;
    }
    
    /**
     * Return true if valid role else false.
     * @param role role to check.
     * @return true if valid role else false.
     */
    public static boolean isValidRole(String role){
        AccountType type;
        try{
            type  = AccountType.valueOf(role);
        } catch (IllegalArgumentException ex) {  
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return this.roleName;
    }    
}
