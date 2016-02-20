package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.common.exceptions.BookingNotFoundException;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidLocationException;
import com.robertnorthard.dtbs.server.layer.model.booking.Booking;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidBookingException;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidGoogleApiResponseException;
import com.robertnorthard.dtbs.server.common.exceptions.RouteNotFoundException;
import com.robertnorthard.dtbs.server.common.exceptions.TaxiNotFoundException;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;
import java.util.List;
import javax.ejb.Local;

/**
 * An interface for defining and enforcing operations needed for the Booking
 * Service class.
 *
 * @author robertnorthard
 */
@Local
public interface BookingFacade {

    /**
     * Find booking by id.
     *
     * @param id id of taxi.
     * @return booking or null if not found.
     */
    public Booking findBooking(Long id);

    /**
     * Return booking if booking id matches the authenticated user, else null.
     *
     * @param id id of booking.
     * @param username username of authenticated user.
     * @return return booking if booking id matches the authenticated user, else
     * null.
     */
    public Booking findBookingForUser(Long id, String username);

    /**
     * Book a taxi.
     *
     * @param booking booking data transfer object. Requires: - username
     * username of passenger. - numberPassengers number of passengers. - pickup
     * location - destination location
     *
     * @return the created booking.
     * @throws AccountAuthenticationFailed invalid username.
     * @throws InvalidLocationException invalid location/not found.
     * @throws RouteNotFoundException route not found.
     * @throws InvalidGoogleApiResponseException unable to parse Google API
     * response.
     * @throws InvalidBookingException invalid booking e.g. user has active
     * bookings.
     */
    public Booking makeBooking(BookingDto booking)
            throws AccountAuthenticationFailed,
            InvalidLocationException,
            RouteNotFoundException,
            InvalidGoogleApiResponseException,
            InvalidBookingException;

    /**
     * Update the specified booking.
     *
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
     * A collections of bookings. If passenger display booking history. If
     * driver display job history.
     *
     * @param username username.
     * @return booking history.
     */
    public List<Booking> findBookingHistory(String username);

    /**
     * Accept a taxi booking.
     *
     * @param username of taxi driver.
     * @param bookingId booking id.
     * @throws TaxiNotFoundException taxi not found.
     * @throws BookingNotFoundException booking not found.
     * @throws IllegalStateException if booking is in an invalid state.
     */
    public void acceptBooking(String username, long bookingId)
            throws TaxiNotFoundException, BookingNotFoundException;

    /**
     * Drop of passenger.
     *
     * @param username username of the taxi driver.
     * @param bookingId id of booking to update.
     * @param timestamp timestamp of update.
     * @throws BookingNotFoundException booking not found.
     * @throws TaxiNotFoundException username for taxi not found.
     */
    public void pickUpPassenger(String username, long bookingId, long timestamp)
            throws BookingNotFoundException, TaxiNotFoundException;

    /**
     * Pink up passenger.
     *
     * @param username of taxi driver.
     * @param bookingId id of booking to update.
     * @param timestamp timestamp of update.
     * @throws BookingNotFoundException booking not found.
     * @throws TaxiNotFoundException username for taxi not found.
     */
    public void dropOffPassenger(String username, long bookingId, long timestamp)
            throws BookingNotFoundException, TaxiNotFoundException;
    
    /**
     * Cancel a booking.
     * 
     * @param username username of person requesting cancellation of  booking.
     * @param bookingId booking to cancel.
     * @throws BookingNotFoundException booking not found.
     * @throws AccountAuthenticationFailed user does not have permission to cancel booking.
     */
    public void cancelBooking(String username, long bookingId) throws BookingNotFoundException, AccountAuthenticationFailed;
}
