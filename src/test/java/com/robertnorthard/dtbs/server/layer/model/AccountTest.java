package com.robertnorthard.dtbs.server.layer.model;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit tests for Account class.
 * @author robertnorthard
 */
public class AccountTest {

    
    private Account account;
    
    @Before
    public void setUp(){
        account = new Account("johndoe","John Doe", "simple_password", "john_doe@example.com", "07888888826");
        account.setRole(AccountRole.PASSENGER);
    }
    /**
     * Test of checkPassword method, of class Account.
     */
    @Test
    public void testCheckPassword() {
        assertTrue(account.checkPassword("simple_password"));
    }

    /**
     * Test of hasRole method, of class Account.
     */
    @Test
    public void testHasRole() {
        assertFalse(account.hasRole("ADMIN"));
    }
    
    /**
     * Test valid account username.
     */
    @Test
    public void validAccountName(){
        assertTrue(Account.isValidUsername("robert"));
        assertTrue(Account.isValidUsername("robert123"));
        assertFalse(Account.isValidUsername("123robert"));
        assertTrue(Account.isValidUsername("john_doe"));
        assertTrue(Account.isValidUsername("john.doe"));
        assertFalse(Account.isValidUsername("' ; SELECT * FROM ACCOUNT"));
        assertFalse(Account.isValidUsername("|#|_~"));
    }
}
