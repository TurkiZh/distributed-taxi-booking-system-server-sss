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
     */
    public String hashPassword(String password);
    
     /**
     * Check if plain text password matches hash.
     * @param password plaintext password
     * @param hash hashed password
     * @return true if plaintext password and hash match, else false.
     */
    public boolean checkPassword(String password, String hash); 
    
    /**
     * Return base54 decoded string.
     * @param base64Encoding base64 encoding
     * @return base64 decoded string.
     */
    public String base64Decode(final String base64Encoding);
    
    /**
     * Generate a random code.
     * @param validCodeCharacters valid code characters
     * @param length length of code to generate. Length of password must be more than 0.
     * @return a random code with the specified length
     */
    public String generateCode(String validCodeCharacters, int length);
    
    /**
     * Generate code with default valid code characters.
     * @param length length of code to generate
     * @return a random code with with the specified length
     */
    public String generateCode(int length);
}
