package com.robertnorthard.dtbs.server.layer.service.business.rules.account;

import com.robertnorthard.dtbs.server.layer.service.business.rules.account.ValidPhoneNumberSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.Specification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorContext;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.service.entities.Account;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for validating user account numbers.
 *
 * @author robertnorthard
 */
public class ValidPhoneNumberSpecificationTest {

    private final ValidatorResult results;
    private final Specification spec;
    private Account account;

    public ValidPhoneNumberSpecificationTest() {
        results = new ValidatorContext();
        spec = new ValidPhoneNumberSpecification(results);
    }

    @Before
    public void setup() {
        account = new Account();
    }

    /**
     * Test valid phone number. Valid Phone number.
     */
    @Test
    public void isSatisfiedByValidPhoneNumber1() {
        account.setPhoneNumber("08989898923");
        assertTrue(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid phone number. Invalid phone number - not enough digits.
     */
    @Test
    public void isSatisfiedByValidPhoneNumber2() {
        account.setPhoneNumber("075345656");
        assertFalse(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid phone number. Invalid phone number characters rather than
     * digits.
     */
    @Test
    public void isSatisfiedByValidPhoneNumber3() {
        account.setPhoneNumber("sdfsdf");
        assertFalse(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid phone number. Null number.
     */
    @Test
    public void isSatisfiedByValidPhoneNumber4() {
        account.setPhoneNumber(null);
        assertFalse(spec.isSatisfiedBy(account));
    }

    /**
     * Test valid phone number. Null candidate.
     */
    @Test(expected = IllegalArgumentException.class)
    public void isSatisfiedByValidPhoneNumber5() {
        assertFalse(spec.isSatisfiedBy(null));
    }
}
