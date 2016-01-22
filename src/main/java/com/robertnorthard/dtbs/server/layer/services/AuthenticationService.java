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
     * @throws IllegalArgumentException if password or hash null;
     */
    public boolean checkPassword(String password, String hash);
}
