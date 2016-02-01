package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Booking;

/**
 * An interface for defining and enforcing operations needed for 
 * the Booking Service class. It provides the scope of possible 
 * database requests made through the TaxiDAO.
 * @author robertnorthard
 */
public interface BookingFacade{
    
    /**
     * Find booking by id.
     * @param id id of taxi.
     * @return booking or null if not found.
     * @throws EntityNotFoundException taxi not found.
     */
    public Booking findBooking(Long id) throws EntityNotFoundException;
}
