package com.robertnorthard.dtbs.server.layer.security;

import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.AccountRole;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robertnorthard
 */
public class AccountSecurityContextTest {

    private Account account;
    private AccountSecurityContext accountSecurityContext;
    
    @Before
    public void setUp() {
        account = new Account("johndoe","John Doe", "simple_password", "john_doe@example.com", "07888888826");
        account.setRole(AccountRole.PASSENGER);
        accountSecurityContext = new AccountSecurityContext(account, null);
    }

    /**
     * Test of isUserInRole method, of class AccountSecurityContext.
     */
    @Test
    public void testIsUserInRole() {
        assertTrue(this.accountSecurityContext.isUserInRole("passenger"));
        assertTrue(this.accountSecurityContext.isUserInRole("PASSENGER"));
        account.setRole(AccountRole.DRIVER);
        assertFalse(this.accountSecurityContext.isUserInRole("passenger"));
        assertFalse(this.accountSecurityContext.isUserInRole("PASSENGER"));
        assertFalse(this.accountSecurityContext.isUserInRole("unknown"));
    }

    
}
