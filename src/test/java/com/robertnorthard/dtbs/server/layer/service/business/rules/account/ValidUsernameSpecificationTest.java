package com.robertnorthard.dtbs.server.layer.service.business.rules.account;

import com.robertnorthard.dtbs.server.layer.service.business.rules.account.ValidUsernameSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.Specification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorContext;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.service.entities.Account;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author robertnorthard
 */
public class ValidUsernameSpecificationTest {

    private final ValidatorResult results;
    private final Specification spec;
    private Account account;

    public ValidUsernameSpecificationTest() {
        results = new ValidatorContext();
        spec = new ValidUsernameSpecification(results);
    }
    
    @Before
    public void setup(){
        account = new Account();
    }

    /**
     * Test valid account username.
     */
    @Test
    public void isSatisfiedByValidUsername1() {
        account.setUsername("robert");
        assertTrue(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid account username. Invalid username.
     */
    @Test
    public void isSatisfiedByValidUsername2() {
        account.setUsername("123robert");
        assertFalse(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid account username. Invalid username.
     */
    @Test
    public void isSatisfiedByValidUsername3() {
        account.setUsername("john_doe");
        assertTrue(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid account username. Valid username.
     */
    @Test
    public void isSatisfiedByValidUsername4() {
        account.setUsername("john.doe");
        assertTrue(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid account username. Invalid user name to test SQL injections.
     */
    @Test
    public void isSatisfiedByValidUsername5() {
        account.setUsername("' ; SELECT * FROM ACCOUNT");
        assertFalse(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid account username. Invalid username with special characters.
     */
    @Test
    public void isSatisfiedByValidUsername6() {
        account.setUsername("\"|#|_~\"");
        assertFalse(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid account username. Null parameter.
     */
    @Test(expected = IllegalArgumentException.class)
    public void isSatisfiedByValidUsername7() {
        spec.isSatisfiedBy(null);
    }
}
