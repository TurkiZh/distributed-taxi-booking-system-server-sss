package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.AccountAlreadyExistsException;
import com.robertnorthard.dtbs.server.common.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.common.exceptions.AccountInvalidException;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.Gender;
import com.robertnorthard.dtbs.server.layer.persistence.AccountDao;
import com.robertnorthard.dtbs.server.layer.persistence.PasswordResetEventDao;
import com.robertnorthard.dtbs.server.layer.utils.mail.MailStrategy;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author robertnorthard
 */
public class AccountServiceTest {

    private AccountFacade accountService;
    private AccountDao accountDao;
    private PasswordResetEventDao passwordResetEventDao;
    private MailStrategy mailStrategy;
    private Account passenger;

    public AccountServiceTest() {
    }

    @Before
    public void setUp() {
        accountDao = Mockito.mock(AccountDao.class);
        passwordResetEventDao = Mockito.mock(PasswordResetEventDao.class);
        mailStrategy = Mockito.mock(MailStrategy.class);

        accountService = new AccountService(accountDao, passwordResetEventDao, mailStrategy);

        passenger = new Account("john.doe", "John","Doe",Gender.MALE, new Date(), "$2a$12$PCE2KE0b26mrJSfbxU8ep.XGxVisAc9BqJbxR6FD9BFLLAYGNipg.",  "07888888826", "john_doe@email.com");
    }
    
    /**
     * Test of registerAccount method, of class AccountService. When account
     * already exists.
     */
    @Test(expected = AccountAlreadyExistsException.class)
    public void testRegisterAccountAccountAlreadyRegistered() throws Exception {
        when(accountDao.findEntityById("john.doe")).thenReturn(passenger);
        this.accountService.registerAccount(this.passenger);
        fail();
    }

    /**
     * Test of registerAccount method, of class AccountService. When account is
     * null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterAccountAccountNull() throws Exception {
        this.accountService.registerAccount(null);
        fail();
    }

    /**
     * Test of registerAccount method, of class AccountService. Invalid email.
     */
    @Test(expected = AccountInvalidException.class)
    public void testRegisterAccountInvalidEmail() throws Exception {
        when(mailStrategy.isValidEmail("john_doe@email.com")).thenReturn(false);
        this.accountService.registerAccount(passenger);
        fail();
      
    }

    /**
     * Test of registerAccount method, of class AccountService. Invalid email.
     */
    @Test(expected = AccountInvalidException.class)
    public void testRegisterAccountInvalidUsername() throws Exception {
        this.accountService.registerAccount(passenger);
        fail();
    }

    /**
     * Test of registerAccount method, of class AccountService. Successful
     * registration.
     */
    @Test
    public void testRegisterAccountValidAccount() throws Exception {
        when(mailStrategy.isValidEmail("john_doe@email.com")).thenReturn(true);
        this.accountService.registerAccount(passenger);
    }
    
   /**
     * Test of findAccount method, of class AccountService,
     * with valid username.
     */
    @Test
    public void testFindAccountValidUsername() {
        when(accountDao.findEntityById("john.doe")).thenReturn(passenger);
        String accountUsername = "john.doe";
        Account johnDoeAccount = this.accountService.findAccount(accountUsername);
        assertEquals(passenger, johnDoeAccount);
    }
    
   /**
     * Test of findAccount method, of class AccountService,
     * with non existent account. 
     * 
     */
    @Test
    public void testFindAccountNoAccount() {
        when(accountDao.findEntityById("john.doe")).thenReturn(passenger);
        String accountUsername = "john";
        Account johnDoeAccount = this.accountService.findAccount(accountUsername);
        assertTrue(johnDoeAccount == null);
    }
    
   /**
     * Test of findAccount method, of class AccountService,
     * with null argument.
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindAccountNullArgument() {
        String accountUsername = null;
        Account foundAccount = this.accountService.findAccount(null);
        assertTrue(foundAccount == null);
    }
    
   /**
     * Test of authenticate method, of class AccountService,
     * with valid account.
     * 
     */
    @Test
    public void testAuthenticateValidCredentials() throws Exception {
        String username = "john.doe";
        String password = "robertnorthard";
        when(accountDao.findEntityById(username)).thenReturn(passenger);
        Account foundAccount = this.accountService.authenticate(username, password);
        assertEquals(passenger, foundAccount);
    }
    
   /**
     * Test of authenticate method, of class AccountService,
     * with valid account.
     * 
     */
    @Test(expected = AccountAuthenticationFailed.class)
    public void testAuthenticateInValidCredentials() throws Exception {
        String username = "john.doe";
        String password = "wrong_password";
        when(accountDao.findEntityById(username)).thenReturn(passenger);
        Account foundAccount = this.accountService.authenticate(username, password);
        fail();
    }
}
