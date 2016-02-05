
package com.robertnorthard.dtbs.server.layer.model.booking;

import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.Route;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import com.robertnorthard.dtbs.server.layer.model.Vehicle;
import com.robertnorthard.dtbs.server.layer.model.VehicleType;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit Tests for entity class booking.
 * 
 * @author robertnorthard
 */
public class BookingTest {

    private VehicleType type;
    private Vehicle vehicle; 
    private Account passenger; 
    private Route route; 
    private Account driver; 
    private Taxi taxi; 
    private Booking booking;
    
    @Before
    public void setUp(){
        type = new VehicleType("Taxi","Ford","Focus",0.3);
        vehicle = new Vehicle("AS10 AJ", 3, type);
        passenger = new Account("robert","password","example@example.com");
        route = new Route();
        route.setDistance(10);
        driver = new Account("driver","password","example@example.com");
        vehicle = new Vehicle("AS10 AJ", 3, type);
        taxi = new Taxi(vehicle, driver);
        booking = new Booking(passenger,route, 2);
    }
    /**
     * Test state transitions.
     */
    @Test
    public void bookingSunnyDayTest(){
        Date date = new Date();
        assertTrue(booking.getState() instanceof AwaitingTaxiBookingState);
        booking.dispatchTaxi(taxi); 
        assertTrue(booking.getState() instanceof TaxiDispatchedBookingState);
        booking.pickupPassenger(new Date(date.getTime()+100));
        assertTrue(booking.getState() instanceof PassengerPickedUpBookingState);
        booking.dropOffPassenger(new Date(date.getTime()+200));    
        assertTrue(booking.getState() instanceof CompletedBookingState);
    }
    
    /**
     * Test invalid pickup time.
     */
    @Test(expected = IllegalArgumentException.class)
    public void bookingInvalidPickupTime(){
        
        Date date = new Date(); 
        booking.dispatchTaxi(taxi); 
        booking.pickupPassenger(new Date(date.getTime()));
        booking.dropOffPassenger(new Date(date.getTime()+200));    
    }
    
   /**
     * Test passenger drop off time before pickup time.
     */
    @Test(expected = IllegalArgumentException.class)
    public void bookingInvalidDropOffTime(){
        Date date = new Date(); 
        booking.dispatchTaxi(taxi); 
        booking.pickupPassenger(new Date(date.getTime()+100));
        booking.dropOffPassenger(new Date(date.getTime()));    
    }
    
    /**
     * Test get number passengers
     */
    @Test
    public void getSetNumberPassengers(){
        booking.setNumberPassengers(5);
        assertTrue(this.booking.getNumberPassengers() == 5);
    }
}