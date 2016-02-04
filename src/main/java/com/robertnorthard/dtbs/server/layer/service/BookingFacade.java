package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidLocationException;
import com.robertnorthard.dtbs.server.layer.model.booking.Booking;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidBookingException;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidGoogleApiResponseException;
import com.robertnorthard.dtbs.server.common.exceptions.RouteNotFoundException;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;
import java.util.List;

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
     */
    public Booking findBooking(Long id);
    
   /**
     * Return booking if booking id matches the authenticated user, else null.
     * 
     * @param id id of booking.
     * @param username username of authenticated user.
     * @return return booking if booking id matches the authenticated user, else null.
     */
    public Booking findBookingForUser(Long id, String username);
    
    /**
     * Book a taxi.
     * @param booking booking data transfer object. Requires:
     * - username username of passenger.
     * - numberPassengers number of passengers.
     * - pickup location
     * - destination location
     * 
     * @return the created booking.
     * @throws AccountAuthenticationFailed invalid username.
     * @throws InvalidLocationException invalid location/not found.
     * @throws RouteNotFoundException route not found.
     * @throws InvalidGoogleApiResponseException unable to parse Google API response.
     * @throws InvalidBookingException invalid booking e.g. user has active bookings.
     */
    public Booking makeBooking(BookingDto booking) 
            throws AccountAuthenticationFailed, 
            InvalidLocationException,
            RouteNotFoundException,
            InvalidGoogleApiResponseException,
            InvalidBookingException;
    
    /**
     * Update the specified booking.
     * @param booking update booking.
     */
    public void updateBooking(Booking booking);
    
    /**
     * Find all bookings in awaiting taxi to be dispatched state.
     * 
     * @return all bookings in awaiting taxi to be dispatched state. 
     */
    public List<Booking> findBookingsInAwaitingTaxiDispatchState();
    
    /**
     * A collections of bookings. 
     * If passenger display booking history. 
     * If driver display job history.
     * 
     * @param username username.
     * @return booking history.
     */
    public List<Booking> findBookingHistory(String username);
}
