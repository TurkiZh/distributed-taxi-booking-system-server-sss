package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.common.exceptions.BookingNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.booking.Booking;
import com.robertnorthard.dtbs.server.layer.persistence.BookingDao;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidBookingException;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidGoogleApiResponseException;
import com.robertnorthard.dtbs.server.common.exceptions.RouteNotFoundException;
import com.robertnorthard.dtbs.server.common.exceptions.TaxiNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.Route;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import com.robertnorthard.dtbs.server.layer.persistence.RouteDao;
import com.robertnorthard.dtbs.server.layer.persistence.TaxiDao;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 * A class implementing the booking facade. It provides the scope of possible
 * database requests made through the booking dao.
 *
 * @author robertnorthard
 */
public class BookingService implements BookingFacade {

    @Inject private BookingDao bookingDao;
    @Inject private AccountFacade accountService;
    @Inject private RouteDao routeDao;
    @Inject private TaxiDao taxiDao;
    @Inject private GoogleDistanceMatrixFacade googleDistanceMatrixFacade ;

    public BookingService(){}
    
    @Override
    public Booking findBooking(Long id) {
        return this.bookingDao.findEntityById(id);
    }

    /**
     * Return booking if booking id matches the authenticated user, else null.
     *
     * @param id id of booking.
     * @param username username of authenticated user.
     * @return return booking if booking id matches the authenticated user, else
     * null.
     */
    @Override
    public Booking findBookingForUser(Long id, String username) {
        Booking booking = this.findBooking(id);

        if (booking != null && booking.getPassenger().getUsername().equals(username)) {
            return booking;
        } else {
            return null;
        }
    }

    /**
     * Book a taxi.
     *
     * @param bookingDto booking data transfer object. Requires: - username
     * username of passenger. - numberPassengers number of passengers. - pickup
     * location - destination location
     * @throws AccountAuthenticationFailed invalid username.
     * @throws RouteNotFoundException route not found.
     * @throws InvalidGoogleApiResponseException invalid response from Google
     *        (Invalid JSON as there API has changed).
     * @throws InvalidBookingException invalid booking e.g. user has active bookings.
     */
    @Override
    public Booking makeBooking(final BookingDto bookingDto)
            throws AccountAuthenticationFailed,
            RouteNotFoundException,
            InvalidGoogleApiResponseException,
            InvalidBookingException{

        try {
            if (bookingDto.getPassengerUsername() == null) {
                throw new IllegalArgumentException("Username cannot be null.");
            }

            Account passenger = this.accountService.findAccount(bookingDto.getPassengerUsername());

            if (passenger == null) {
                throw new AccountAuthenticationFailed();
            }
            
            if(this.incompleteBookings(passenger.getUsername())){
                throw new InvalidBookingException("A user can only have one active booking.");
            }

            Route route = this.googleDistanceMatrixFacade.getRouteInfo(
                    bookingDto.getStartLocation(),
                    bookingDto.getEndLocation());

            if (route == null) {
                throw new RouteNotFoundException();
            }

            Booking booking = new Booking(
                    passenger,
                    route,
                    bookingDto.getNumberPassengers());

            this.bookingDao.persistEntity(booking);

            return booking;

        } catch (InvalidGoogleApiResponseException ex) {
            throw ex;
        }
    }
    
    /**
     * Return true if incomplete bookings for user else false.
     * 
     * @param username username to check.
     * @return true if incomplete bookings for user else false
     */
    private boolean incompleteBookings(String username){
        return this.bookingDao.findInCompletedBookingsForUser(username).size() >= 1;
    }

    /**
     * Update booking.
     *
     * @param booking booking to update.
     */
    @Override
    public void updateBooking(Booking booking) {
        this.bookingDao.update(booking);
    }

    /**
     * Find all bookings in awaiting taxi to be dispatched state.
     *
     * @return all bookings in awaiting taxi to be dispatched state.
     */
    @Override
    public List<Booking> findBookingsInAwaitingTaxiDispatchState() {
        return this.bookingDao.findBookingInState(Booking.getAwaitingTaxiBookingState());
    }

    /**
     * A collections of bookings. If passenger display booking history. If
     * driver display job history. If passenger has multiple roles combine collections.
     *
     * @param username username.
     * @return return booking history.
     */
    @Override
    public List<Booking> findBookingHistory(String username) {

        List<Booking> bookingHistory = new ArrayList<>();
        
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be nill.");
        }

        Account account = this.accountService.findAccount(username);
        if (account != null) {

            // append as a passenger can have multiple roles.
            if (account.hasRole("passenger")) {
                bookingHistory.addAll(this.bookingDao.findBookingsForPassenger(username));
            }
            if (account.hasRole("driver")) {
                bookingHistory.addAll(this.bookingDao.findBookingsForDriver(username));
            }

            return bookingHistory;
        }

        return bookingHistory;
    }
    
    /**
     * Accept a taxi booking.
     * 
     * @param username username of taxi driver.
     * @param bookingId booking id.
     * @throws TaxiNotFoundException taxi not found.
     * @throws BookingNotFoundException booking not found.
     * @throws IllegalStateException if booking is in an invalid state.
     */
    @Override
    public synchronized void acceptBooking(String username, long bookingId) 
            throws TaxiNotFoundException,BookingNotFoundException {
        
        Taxi taxi = this.taxiDao.findTaxiForDriver(username);
        Booking booking = this.findBooking(bookingId);
        
        if(taxi == null){
            throw new TaxiNotFoundException();
        }
        
        if(booking == null){
            throw new BookingNotFoundException();
        }
        
        try{        
            booking.dispatchTaxi(taxi);
            this.bookingDao.update(booking);
        }catch(IllegalStateException ex){
            throw ex;
        }
    }
    
   /**
     * Pink up passenger.
     * 
     * @param username username or driver accepting the booking
     * @param bookingId id of booking to update.
     * @param timestamp timestamp of update.
     * @throws BookingNotFoundException booking not found.
     * @throws TaxiNotFoundException taxi for user not found.
     */
    @Override
    public void pickUpPassenger(String username, long bookingId, long timestamp) 
            throws BookingNotFoundException, TaxiNotFoundException {
        
        Booking booking = this.findBooking(bookingId);
        Taxi taxi = this.taxiDao.findTaxiForDriver(username);
        
        if(taxi == null){
            throw new TaxiNotFoundException();
        }
        
        if(booking == null){
            throw new BookingNotFoundException();
        }
         
        try{        
            booking.pickupPassenger(new Date(timestamp));
            this.bookingDao.update(booking);
        }catch(IllegalStateException ex){
            throw ex;
        }
    }

   /**
     * Drop of passenger.
     * 
     * @param bookingId id of booking to update.
     * @param timestamp timestamp of update.
     * @throws BookingNotFoundException booking not found.
     * @throws TaxiNotFoundException taxi for user not found.
     */
    @Override
    public void dropOffPassenger(String username, long bookingId, long timestamp) 
            throws BookingNotFoundException,TaxiNotFoundException{
        
        Booking booking = this.findBooking(bookingId);
        
        Taxi taxi = this.taxiDao.findTaxiForDriver(username);
        
        if(taxi == null){
            throw new TaxiNotFoundException();
        }
        
        if(booking == null){
            throw new BookingNotFoundException();
        }
         
        try{        
            booking.dropOffPassenger(new Date(timestamp));
            this.bookingDao.update(booking);
        }catch(IllegalStateException ex){
            throw ex;
        }
    }
}
