package com.robertnorthard.dtbs.server.layer.service.entities.taxi;

import com.robertnorthard.dtbs.server.layer.service.entities.taxi.AcceptedJobTaxiState;
import com.robertnorthard.dtbs.server.layer.service.entities.taxi.OnDutyTaxiState;
import com.robertnorthard.dtbs.server.layer.service.entities.taxi.OffDutyTaxiState;
import com.robertnorthard.dtbs.server.layer.service.entities.taxi.Taxi;
import com.robertnorthard.dtbs.server.layer.service.entities.Account;
import com.robertnorthard.dtbs.server.layer.service.entities.AccountRole;
import com.robertnorthard.dtbs.server.layer.service.entities.Route;
import com.robertnorthard.dtbs.server.layer.service.entities.Vehicle;
import com.robertnorthard.dtbs.server.layer.service.entities.VehicleType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Taxi class.
 *
 * @author robertnorthard
 */
public class TaxiTest {

    private VehicleType type;
    private Vehicle vehicle;
    private Route route;
    private Account driver;
    private Taxi taxi;

    @Before
    public void setUp() {
        driver = new Account("johndoe", "John", "Doe", "simple_password", "john_doe@example.com", "07888888826");
        driver.setRole(AccountRole.DRIVER);

        type = new VehicleType("Taxi", "Ford", "Focus", 0.3);
        vehicle = new Vehicle("AS10 AJ", 3, type);
        taxi = new Taxi(vehicle, driver);
    }

    /*
     * 
     * Tests for valid taxi state transition.
     *
     */
    
    /**
     * Test initial method state.
     */
    @Test
    public void testMethodConstructror() {
        assertTrue(taxi.getState() instanceof OffDutyTaxiState);
    }

    /**
     * Test goOff duty method
     */
    @Test
    public void testMethodGoOnDuty() {
        taxi.goOnDuty();
        assertTrue(taxi.getState() instanceof OnDutyTaxiState);
    }

    /**
     * Test accepted job taxi state method.
     */
    @Test
    public void testMethodAcceptedJobTaxiState() {
        taxi.goOnDuty();
        taxi.acceptJob();
        assertTrue(taxi.getState() instanceof AcceptedJobTaxiState);
    }

    /**
     * Test goOff duty method.
     */
    @Test
    public void testMethodGoOfDuty() {
        taxi.goOnDuty();
        taxi.goOffDuty();
        assertTrue(taxi.getState() instanceof OffDutyTaxiState);
    }
    
    // End of valid state transition tests.
}
