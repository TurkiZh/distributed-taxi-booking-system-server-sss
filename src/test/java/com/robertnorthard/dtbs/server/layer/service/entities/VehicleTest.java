package com.robertnorthard.dtbs.server.layer.service.entities;

import com.robertnorthard.dtbs.server.layer.service.entities.Vehicle;
import com.robertnorthard.dtbs.server.layer.service.entities.VehicleType;
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
    public void setUp() {
        type = new VehicleType("Taxi", "Ford", "Focus", 0.3);
        vehicle = new Vehicle("AS10 AJ", 3, type);
    }

    /**
     * Test check seat availability.
     */
    @Test
    public void testCheckSeatchAvailability() {

        this.vehicle.setNumberSeats(5);
        assertTrue(this.vehicle.getNumberSeats() == 5);
    }
}
