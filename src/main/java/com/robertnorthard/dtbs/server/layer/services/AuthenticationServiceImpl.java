package com.robertnorthard.dtbs.server.layer.services;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Authentication Service provide functions for managing user credentials.
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
     * @throws IllegalArgumentException if password or hash null.
     */
    @Override
    public boolean checkPassword(String password, String hash) 
            throws IllegalArgumentException{
        
        if(password == null || hash==null){
            IllegalArgumentException e = new IllegalArgumentException(
                    "Password and hash cannot be null");
            LOGGER.log(Level.WARNING,e.getMessage());
            throw e;
        }
            
        return BCrypt.checkpw(password, hash);
    }

    /**
     * Generate a random code.
     * @param validCodeCharacters valid code characters
     * @param length length of code to generate. Length of password must be > 0.
     * @return a random code with the specified length
     * @throw IllegalArgumentException length and validCodeCharacters cannot be 0. 
     */
    @Override
    public String generateCode(String validCodeCharacters, int length)
            throws IllegalArgumentException{
        
        if(length== 0 || validCodeCharacters.length() == 0){
             IllegalArgumentException e = new IllegalArgumentException(
                     "Password or validCodeCharacters length cannot be 0");
             LOGGER.log(Level.WARNING,e.getMessage());
             throw e;
        }
        
        Random rand = new Random();
        
        StringBuilder sBuilder = new StringBuilder();
        
        while(sBuilder.length() < length ){
            sBuilder.append(
                    validCodeCharacters.charAt(
                            rand.nextInt(
                                validCodeCharacters.length())));
        }
        
        return sBuilder.toString();
    }
    
    /**
     * Generate code with default valid code characters.
     * @param length length of code to generate
     * @return a random code with with the specified length
     * @IllegalArgumentException length cannot be 0. 
     */
    @Override
    public String generateCode(int length) 
            throws IllegalArgumentException{
        return this.generateCode(
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567", length);
    }
}