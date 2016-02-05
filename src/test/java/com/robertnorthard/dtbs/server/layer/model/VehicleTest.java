package com.robertnorthard.dtbs.server.layer.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Vehicle entity.
 * 
 * @author robertnorthard
 */
public class VehicleTest {
    
        private VehicleType type;
        private Vehicle vehicle;
    
        @Before
        public void setUp(){
            type = new VehicleType("Taxi","Ford","Focus",0.3);
            vehicle = new Vehicle("AS10 AJ", 3, type);
        }
        
        /**
         * Test check seat availability.
         */
        @Test
        public void testCheckSeatchAvailability(){
            
            this.vehicle.setNumberSeats(5);
            assertTrue(this.vehicle.getNumberSeats() == 5);
            // Only four seats available as one allocated to the driver.
            assertFalse(this.vehicle.checkseatAvailability(5));
   
        }
}
