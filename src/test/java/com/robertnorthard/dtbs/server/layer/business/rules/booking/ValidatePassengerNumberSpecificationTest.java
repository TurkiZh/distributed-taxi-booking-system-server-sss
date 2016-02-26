/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robertnorthard.dtbs.server.layer.business.rules.booking;

import com.robertnorthard.dtbs.server.layer.business.rules.Specification;
import com.robertnorthard.dtbs.server.layer.business.rules.validator.ValidatorContext;
import com.robertnorthard.dtbs.server.layer.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class ValidPassengerNumberSpecification.
 * 
 * @author robertnorthard
 */
public class ValidatePassengerNumberSpecificationTest {
    
    private final ValidatorResult results;
    private final Specification spec;
    private BookingDto booking;

    public ValidatePassengerNumberSpecificationTest() {
        results = new ValidatorContext();
        spec = new ValidatePassengerNumberSpecification(results);
    }
    
    @Before
    public void setup(){
        booking = new BookingDto();
    }

    /**
     * Test valid passenger number.
     * Test: 0 - invalid.
     */
    @Test
    public void isSatisfiedByValidPassenger1() {
        booking.setNumberPassengers(0);
        assertFalse(spec.isSatisfiedBy(booking));
    }
    
    /**
     * Test valid passenger number.
     * Test: -1 - invalid.
     */
    @Test
    public void isSatisfiedByValidPassenger2() {
        booking.setNumberPassengers(-1);
        assertFalse(spec.isSatisfiedBy(booking));
    }
    
    /**
     * Test valid passenger number.
     * Test: 1 - valid.
     */
    @Test
    public void isSatisfiedByValidPassenger3() {
        booking.setNumberPassengers(1);
        assertTrue(spec.isSatisfiedBy(booking));
    }
    
    
    /**
     * Test valid passenger number.
     * Test: null bookingDto.
     */
    @Test(expected = IllegalArgumentException.class)
    public void isSatisfiedByValidPassenger4() {
        spec.isSatisfiedBy(null);
        
        fail("IllegalArgumentException should have been thrown.");
    }
    
}
