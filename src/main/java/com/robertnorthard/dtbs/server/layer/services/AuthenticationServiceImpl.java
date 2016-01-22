package com.robertnorthard.dtbs.server.layer.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Authentication Service to provide functions to authenticate users.
 * @author robertnorthard
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());
    
    /** 
     * Hash a plaintext value using BCrypt, blowfish block-cipher with a work factor of 12.
     * @param password password to hash.
     * @return a string representation of the salted password.
     */
    @Override
    public String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    /**
     * Check if plain text password matches hash.
     * @param password plaintext password
     * @param hash hashed password
     * @return true if plaintext password and hash match, else false.
     * @throws IllegalArgumentException if password or hash null;
     */
    @Override
    public boolean checkPassword(String password, String hash) 
            throws IllegalArgumentException{
        
        if(password == null || hash==null){
            IllegalArgumentException e = new IllegalArgumentException("Password and hash cannot be null");
            LOGGER.log(Level.WARNING,e.getMessage());
            throw e;
        }
            
        return BCrypt.checkpw(password, hash);
    }
}
