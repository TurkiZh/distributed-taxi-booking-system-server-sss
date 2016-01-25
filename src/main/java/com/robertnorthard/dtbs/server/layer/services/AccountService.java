package com.robertnorthard.dtbs.server.layer.services;

import com.robertnorthard.dtbs.server.exceptions.AccountAlreadyExistsException;
import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.exceptions.AccountInvalidException;
import com.robertnorthard.dtbs.server.exceptions.AccountNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Account;

/**
 * Account Service interface. Manages user accounts.
 * @author robertnorthard
 */
public interface AccountService {
    
    /**
     * Register a new account. 
     * Two user's cannot have the same username.
     * 
     * @param acct account to create.
     * @throws AccountAlreadyExistsException if account already exists.
     * @throws AccountInvalidException if email invalid.
     */
    public void registerAccount(Account acct) 
            throws AccountAlreadyExistsException, AccountInvalidException;
    
    /**
     * Authenticate a user.
     * @param username username of account
     * @param password password of account
     * @return account object if authentication successful else null.
     * @throws AccountAuthenticationFailed if authentication fails.
     */
    public Account authenticate(String username, String password) 
            throws AccountAuthenticationFailed;
    
     /**
     * Return account with corresponding username.
     * @param username username
     * @return an account with the corresponding username. 
     * If the account does not exist
     * return null.
     */
    public Account findAccount(final String username);   
    
    /**
     * Reset account password.
     * @param username username of account to reset.
     * @throws AccountNotFoundException account not found
     */
    public void resetPassword(final String username)
            throws AccountNotFoundException;
    
    /**
     * 
     * @param code temporary authentication code.
     * @param username username to authenticate with.
     * @param newPassword new password for user.
     * @throws AccountAuthenticationFailed if username and code do not match a valid, active password reset event.
     * @throws AccountNotFoundException account not found but password resets exist. Indicates data integrity issues.
     */
    public void resetPassword(final String code, final String username, final String newPassword) 
            throws AccountAuthenticationFailed, AccountNotFoundException;
}
