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
        account = new Account("johndoe","John", "Doe", "simple_password", "john_doe@example.com", "07888888826");
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
     * Intent: to test if enum comparison works as expected, due to previous defects.
     */
    @Test
    public void testHasRole() {
        assertFalse(account.hasRole("ADMIN"));
        assertTrue(account.hasRole("PASSENGER"));
        assertTrue(account.hasRole("passenger"));
        assertFalse(account.hasRole("unknown"));
    }
}
