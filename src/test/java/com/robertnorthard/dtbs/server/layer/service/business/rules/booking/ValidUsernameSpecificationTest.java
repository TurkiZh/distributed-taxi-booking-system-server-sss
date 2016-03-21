package com.robertnorthard.dtbs.server.layer.service.business.rules.booking;

import com.robertnorthard.dtbs.server.layer.service.business.rules.booking.ValidUsernameSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.Specification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorContext;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;
import org.junit.Test;

/**
 * Unit tests for class ValidUsernameSpecification.
 * 
 * @author robertnorthard
 */
public class ValidUsernameSpecificationTest {
    
    private final ValidatorResult results;
    private final Specification spec;

    public ValidUsernameSpecificationTest() {
        results = new ValidatorContext();
        spec = new ValidUsernameSpecification(results);
    }
    
    /**
     * Test valid account username. Null parameter.
     */
    @Test(expected = IllegalArgumentException.class)
    public void isSatisfiedByValidUsername7() {
        spec.isSatisfiedBy(null);
    }
}
