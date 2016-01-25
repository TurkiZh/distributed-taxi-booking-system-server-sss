package com.robertnorthard.dtbs.server.layer.services;

import com.robertnorthard.dtbs.server.exceptions.AccountAlreadyExistsException;
import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.exceptions.AccountInvalidException;
import com.robertnorthard.dtbs.server.exceptions.AccountNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.PasswordResetEvent;
import com.robertnorthard.dtbs.server.layer.persistence.AccountDao;
import com.robertnorthard.dtbs.server.layer.persistence.PasswordResetEventDao;
import com.robertnorthard.dtbs.server.layer.utils.mail.MailStrategy;
import com.robertnorthard.dtbs.server.layer.utils.mail.SmtpMailStrategy;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Account Service interface implementation.
 * @author robertnorthard
 */
public class AccountServiceImpl implements AccountService{

    private final AccountDao dao;
    private final PasswordResetEventDao passwordResetDao;
    private final AuthenticationService authService;
    private final MailStrategy mailStrategy;
    
    /**
     * Default Constructor
     */
    public AccountServiceImpl(){
        this.dao = new AccountDao();
        this.passwordResetDao = new PasswordResetEventDao();
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
    public void registerAccount(final Account acct) 
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
            this.mailStrategy.sendMail("DTBS - Registration Confirmation", 
                    "Your account, with username "  + acct.getUsername() + 
                            " has been activated.", acct.getEmail());
        }else{
            throw new AccountAlreadyExistsException(
                    String.format("Account with username - [%s] already exists.",acct.getUsername()));
        }
    }

    /**
     * Return account with corresponding username.
     * @param username username.
     * @return an account with the corresponding username. 
     * If the account does not exist
     * return null.
     */
    @Override
    public Account findAccount(final String username) {
        return this.dao.findEntityById(username);       
    }

    /**
     * Authenticate a user.
     * @param username username of account
     * @param password password of account
     * @return account object if authentication successful else null.
     * @throws AccountAuthenticationFailed if authentication fails.
     */
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

    /**
     * Reset account password via temporary code.
     * 1 - Generate temporary code
     * 2 - Create reset event.
     * 3 - Send email to use with temporary access code.
     * @param username username of account to reset.
     * @throws AccountNotFoundException account not found.
     */
    @Override
    public void resetPassword(final String username) 
            throws AccountNotFoundException {
        
        Account account = this.findAccount(username);
        
        if(account == null){
            throw new AccountNotFoundException();
        }
        
        // generate temporary reset code
        String resetCode = this.authService.generateCode(4);
        
        // calculate reset expiry
        DateTime expireDate = new DateTime().plusDays(1);
        
        // create new password reset event with reset code, account username and expiry.
        PasswordResetEvent event = new PasswordResetEvent(
                username,resetCode,expireDate);
        
        // Set all current password resets for the user iactive.
        this.deactivePasswordResets(username);
        
        // save password reset event
        this.passwordResetDao.persistEntity(event);
        
        // send email with temporary code
        this.mailStrategy.sendMail("DTBS - Reset Password", 
                "Your temporary code to reset your password is " 
                        + resetCode + " and expires on " 
                        + expireDate.toString("DD-MM-YYYY hh:mm:ss"),
                account.getEmail());
    }
    
    /**
     * 
     * @param code temporary authentication code.
     * @param username username to authenticate with.
     * @param newPassword new password for user.
     * @throws AccountAuthenticationFailed if username and code do not match a valid, active password reset event.
     * @throws AccountNotFoundException account not found but password resets exist. Indicates data integrity issues. 
     */
    @Override
    public void resetPassword(final String code, final String username, final String newPassword) 
            throws AccountAuthenticationFailed, AccountNotFoundException{
        
        // authenticate code.
        List<PasswordResetEvent> events = this.passwordResetDao.findActivePasswordResetByUsername(username);
        
        if(authenticateTemporaryCredentials(username, code)){
            
            Account account = this.findAccount(username);
            
            if(account != null){
                
                // create hash of new password
                String passwordHash = this.authService.hashPassword(newPassword);
                
                // store password hash
                account.setPassword(passwordHash);
                
                //update account
                this.dao.update(account);

                // on success deactive all password resets for account
                this.deactivePasswordResets(username);
            }else{
                throw new AccountNotFoundException();
            }
        }else{
            throw new AccountAuthenticationFailed();
        }   
    }
    
    private boolean authenticateTemporaryCredentials(final String username, final String code){
        // authenticate code.
        List<PasswordResetEvent> events = this.passwordResetDao.findActivePasswordResetByUsername(username);
        
        for(PasswordResetEvent event: events){
            if(event.validateCode(code)){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Set all active password resets for the specified user inactive.
     * @param username username to search by.
     */
    private void deactivePasswordResets(String username){
        List<PasswordResetEvent> events = this.passwordResetDao.findActivePasswordResetByUsername(username);
        
        for(PasswordResetEvent e: events){
            e.setInactive();
            this.passwordResetDao.update(e);
        }
    }

    /**
     * Authenticate user from base64 encoded message.
     * @param base64HttpCredentials base64 encoded credentials
     * @return account object if authentication successful else null.
     * @throws AccountAuthenticationFailed if authentication fails.
     */
    @Override
    public Account authenticate(String base64HttpCredentials) throws AccountAuthenticationFailed {
        
        Account authAccount = null;
        String[] credentials = null;
        
        try {
            // Remove HTTP bearer e.g. Authorization: Basic base64EncodedUsernameAndPassword
            base64HttpCredentials = base64HttpCredentials.replaceFirst("[Bb]asic ", "");
            
            String base64Decode = this.authService.base64Decode(base64HttpCredentials);
            credentials = base64Decode.split(":");
            System.out.println(credentials.length);
            
            if(credentials.length != 2){
                throw new IllegalArgumentException("Invalid Authorisation token.");
            }
            
            // username - credentials[0]
            // password - credentials[1]
            authAccount = this.authenticate("example", "example");
            
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch(AccountAuthenticationFailed ex){
            throw new AccountAuthenticationFailed();
        }
        
        return authAccount;
    }
}