package com.robertnorthard.dtbs.server.layer.security;

import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.AccountRole;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * integration tests for class AccountSecurityContextTest
 * 
 * @author robertnorthard
 */
public class AccountSecurityContextIT {

    private Account account;
    private AccountSecurityContext accountSecurityContext;
    
    @Before
    public void setUp() {
        account = new Account("johndoe","John", "Doe", "simple_password", "john_doe@example.com", "07888888826");
        account.setRole(AccountRole.PASSENGER);
        accountSecurityContext = new AccountSecurityContext(account, null);
    }

    /**
     * Test of isUserInRole method, of class AccountSecurityContext.
     */
    @Test
    public void testIsUserInRoleValidRole() {
        assertTrue(this.accountSecurityContext.isUserInRole("passenger"));

    }
    
    /**
     * Test if isUserInRole method, of class AccountSecurityContext,
     * when user has role passenger.
     */
    @Test
    public void testIsUserInRolePassengerRole() {
        assertTrue(this.accountSecurityContext.isUserInRole("passenger"));
    }
    
    /**
     * Test if isUserInRole method, of class AccountSecurityContext, 
     * when user has role passenger.
     */
    @Test
    public void testIsUserHasRolePassengerForDriver() {
        account.setRole(AccountRole.DRIVER);
        assertFalse(this.accountSecurityContext.isUserInRole("passenger"));
    }

    /**
     * Test of isUserInRole method, of class AccountSecurityContext, 
     * when user has role passenger and checked role is unknown.
     */
    @Test
    public void testIsUserInRoleUnkownUser() {
        assertFalse(this.accountSecurityContext.isUserInRole("unknown"));
    }
}
