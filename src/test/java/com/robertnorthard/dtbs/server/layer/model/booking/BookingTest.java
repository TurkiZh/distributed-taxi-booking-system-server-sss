
package com.robertnorthard.dtbs.server.layer.model.booking;

import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.Route;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import com.robertnorthard.dtbs.server.layer.model.Vehicle;
import com.robertnorthard.dtbs.server.layer.model.VehicleType;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests for entity class booking.
 * 
 * @author robertnorthard
 */
public class BookingTest {

    @Test
    public void bookingSunnyDayTest(){
        
        VehicleType type = new VehicleType("Lux","Ford","Focus",0.3);
        Account passenger = new Account("robert","password","example@example.com");
        Route route = new Route();
        route.setDistance(10);
        Account driver = new Account("driver","password","example@example.com");
        Vehicle vehicle = new Vehicle("AS10 AJ", 3, type);
        Taxi taxi = new Taxi(vehicle, driver);
        
        Booking booking = new Booking(passenger,route, 2);
        Date date = new Date(); 
        booking.dispatchTaxi(taxi); 
        booking.pickupPassenger(new Date(date.getTime()+100));
        booking.dropOffPassenger(new Date(date.getTime()+200));    
        assertTrue(booking.getState() instanceof CompletedBookingState);
    }
}