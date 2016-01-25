package com.robertnorthard.dtbs.server.layer.services;


/**
 * Authentication Service interface. Manages password hashing and verification.
 * @author robertnorthard
 */
public interface AuthenticationService {
     
    /**
     * Hash a plaintext value using BCrypt, blowfish block-cipher with a work factor of 12.
     * @param password password to hash.
     * @return a string representation of the salted password.
     * @throws IllegalArgumentException if password or hash null.
     */
    public String hashPassword(String password) 
            throws IllegalArgumentException;
    
     /**
     * Check if plain text password matches hash.
     * @param password plaintext password
     * @param hash hashed password
     * @return true if plaintext password and hash match, else false.
     * @throws IllegalArgumentException if password or hash null.
     */
    public boolean checkPassword(String password, String hash) 
            throws IllegalArgumentException; 
    
    
    /**
     * Return username and password from base64 encoded string.
     * @param base64Encoding base64 encoding
     * @return base64 decoded username and password.
     * @throws IllegalArgumentException if password or hash null.
     */
    public String base64Decode(final String base64Encoding) 
            throws IllegalArgumentException;
    
    /**
     * Generate a random code.
     * @param validCodeCharacters valid code characters
     * @param length length of code to generate. Length of password must be > 0.
     * @return a random code with the specified length
     * @throw IllegalArgumentException length and length of validCodeCharacters cannot be 0. 
     */
    public String generateCode(String validCodeCharacters, int length) 
            throws IllegalArgumentException;
    
    /**
     * Generate code with default valid code characters.
     * @param length length of code to generate
     * @return a random code with with the specified length
     * @IllegalArgumentException length cannot be 0. 
     */
    public String generateCode(int length) 
            throws IllegalArgumentException;
}