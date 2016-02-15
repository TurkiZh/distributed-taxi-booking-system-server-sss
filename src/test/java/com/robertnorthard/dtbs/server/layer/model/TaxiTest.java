package com.robertnorthard.dtbs.server.layer.model;

import com.robertnorthard.dtbs.server.layer.model.taxi.Taxi;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for taxi.
 *
 * @author robertnorthard
 */
public class TaxiTest {

    private VehicleType type;
    private Vehicle vehicle;
    private Taxi taxi;
    private Account driver;

    @Before
    public void setUp() {
        driver = new Account("johndoe", "John", "Doe", "simple_password", "john_doe@example.com", "07888888826");
        driver.setRole(AccountRole.DRIVER);

        type = new VehicleType("Taxi", "Ford", "Focus", 0.3);
        vehicle = new Vehicle("AS10 AJ", 5, type);
        taxi = new Taxi(vehicle, driver);
    }

    /**
     * Test taxi check seat availability.
     */
    @Test
    public void testCheckSeatchAvailability() {

        this.vehicle.setNumberSeats(5);
        assertFalse(this.taxi.checkseatAvailability(4));
    }
}
