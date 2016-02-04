package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.layer.model.booking.Booking;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import java.util.List;

/**
 * An interface for defining and enforcing operations needed for 
 * the Taxi queue class.  It provides the scope of possible 
 * database requests made through the AccountDAO.
 * @author robertnorthard
 */
public interface TaxiQueueFacade extends Runnable {
    
    public void addTaxi(Taxi taxi);
    
    public List<Taxi> getTaxis();
    
    public int getWaitingTaxiCount();
    
    public int getTaxiInOperationCount();
    
    public void addBooking(Booking booking);
    
    public List<Booking> getAllActiveBooking();
    
    public void removeBooking(Booking booking);
}
