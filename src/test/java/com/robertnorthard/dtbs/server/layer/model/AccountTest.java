package com.robertnorthard.dtbs.server.layer.model;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Account class.
 * @author robertnorthard
 */
public class AccountTest {

    /**
     * Test of checkPassword method, of class Account.
     */
    @Test
    public void testCheckPassword() {
        String password = "testpassword";
        Account instance = new Account("testuser",password,"test@test.com");
        boolean expResult = true;
        boolean result = instance.checkPassword(password);
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsername method, of class Account.
     */
    @Test
    public void testGetUsername() {
        String expResult = "username";
        Account instance = new Account(expResult,"testpassword","test@test.com");
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of addRole method, of class Account.
     */
    @Test
    public void testAddRole() {
        String role = "ADMIN";
        Account instance = new Account();
        instance.addRole(role);
        assertTrue(instance.getRoles().contains(role));
    }

    /**
     * Test of hasRole method, of class Account.
     */
    @Test
    public void testHasRole() {
        String role = "ADMIN";
        Account instance = new Account();
        boolean expResult = false;
        boolean result = instance.hasRole(role);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRoles method, of class Account.
     */
    @Test
    public void testGetRoles() {
        Account instance = new Account();
        List<String> result = instance.getRoles();
        assertTrue(result != null);
    }

    /**
     * Test of getPassword method, of class Account.
     */
    @Test
    public void testGetPassword() {
        String expResult = "testpassword";
        Account instance = new Account(expResult,"testpassword","test@test.com");
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Account.
     */
    @Test
    public void testSetPassword() {
        String password = "password";
        Account instance = new Account();
        instance.setPassword(password);
        assertEquals(password, instance.getPassword());
    }

    /**
     * Test of getEmail method, of class Account.
     */
    @Test
    public void testGetEmail() {
        String expResult = "test@test.com";
        Account instance = new Account(expResult,"testpassword","test@test.com");
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEmail method, of class Account.
     */
    @Test
    public void testSetEmail() {
        String email = "test@email.com";
        Account instance = new Account();
        instance.setEmail(email);
        assertEquals(email, instance.getEmail());
    }
    
}
