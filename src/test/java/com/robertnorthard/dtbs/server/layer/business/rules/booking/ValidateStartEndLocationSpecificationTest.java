package com.robertnorthard.dtbs.server.layer.business.rules.booking;

import com.robertnorthard.dtbs.server.layer.business.rules.Specification;
import com.robertnorthard.dtbs.server.layer.business.rules.validator.ValidatorContext;
import com.robertnorthard.dtbs.server.layer.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class ValidateStartEndLocationSpecification.
 * @author robertnorthard
 */
public class ValidateStartEndLocationSpecificationTest {
    
    private final ValidatorResult results;
    private final Specification spec;
    private BookingDto booking;

    public ValidateStartEndLocationSpecificationTest() {
        results = new ValidatorContext();
        spec = new ValidateStartEndLocationSpecification(results);
    }
    
    @Before
    public void setup(){
        booking = new BookingDto();
    }

    /**
     * Test invalid start end location.
     * Test: null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void isSatisfiedByStartEndLocation1() {
        booking.setStartLocation(null);
        booking.setEndLocation(null);
        spec.isSatisfiedBy(booking);
        
        fail("Expected IllegalArgumentException as start and end location is null");
    }
    
    /**
     * Test invalid start end location.
     * Test: same start and end location.
     */
    @Test
    public void isSatisfiedByStartEndLocation2() {
        
        Location location = new Location(90,180);
        
        booking.setStartLocation(location);
        booking.setEndLocation(location);
        assertFalse(spec.isSatisfiedBy(booking));
    }
}
