package com.robertnorthard.dtbs.server.layer.services;

import com.robertnorthard.dtbs.server.exceptions.AccountAlreadyExistsException;
import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.exceptions.AccountInvalidException;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.persistence.AccountDao;
import com.robertnorthard.dtbs.server.layer.utils.mail.MailStrategy;
import com.robertnorthard.dtbs.server.layer.utils.mail.SmtpMailStrategy;

/**
 * Account Service interface implementation.
 * @author robertnorthard
 */
public class AccountServiceImpl implements AccountService{

    private final AccountDao dao;
    private final AuthenticationService authService;
    private final MailStrategy mailStrategy;
    
    /**
     * Default Constructor
     */
    public AccountServiceImpl(){
        this.dao = new AccountDao();
        this.authService = new AuthenticationServiceImpl();
        this.mailStrategy = new SmtpMailStrategy();
    }
    
    /**
     * Register a new account. 
     * Checks if user exists and confirms validity of email. 
     * If email is valid generate hash of user's password and 
     * send email to user to confirm account creation.
     * @param acct account to create.
     * @throws AccountAlreadyExistsException if account already exists.
     * @throws AccountInvalidException invalid email address
     */
    @Override
    public void registerAccount(Account acct) 
            throws AccountAlreadyExistsException, AccountInvalidException{
        
        // check if acount with username already exists
        if(this.dao.findEntityById(acct.getUsername()) == null){
            
            //check if valid email
            if(!this.mailStrategy.isValidEmail(
                    acct.getEmail())){
                throw new AccountInvalidException("Invalid email.");
            }
            
            // generate password hash
            String passwordHash = this.authService.hashPassword(
                    acct.getPassword());
            
            // store password hash
            acct.setPassword(passwordHash);
            
            // persist entity
            this.dao.persistEntity(acct);
            
            // email account registration confirmation.
            this.mailStrategy.sendMail("DTMS - Registration Confirmation", 
                    "Your account, with username "  + acct.getUsername() + 
                            " has been activated.", acct.getEmail());
        }else{
            throw new AccountAlreadyExistsException(
                    String.format("Account with username - [%s] already exists.",acct.getUsername()));
        }
    }

    /**
     * Return account with corresponding username.
     * @param username username
     * @return an account with the corresponding username. 
     * If the account does not exist
     * return null.
     */
    @Override
    public Account findAccount(String username) {
        return this.dao.findEntityById(username);
    }

    @Override
    public Account authenticate(String username, String password) 
            throws AccountAuthenticationFailed{
        Account account = this.findAccount(username);
        
        if(account==null){
            throw new AccountAuthenticationFailed();
        }
        
        if(!this.authService.
                checkPassword(password,account.getPassword())){
             throw new AccountAuthenticationFailed();
        }
        

        return account;
    }
}  