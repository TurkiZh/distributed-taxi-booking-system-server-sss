package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtbs.server.layer.model.booking.Booking;
import com.robertnorthard.dtbs.server.layer.model.booking.BookingState;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A booking Data Access Object (DAO) class for handling and managing event related data
 * requested, updated, and processed in the application and maintained in the
 * database.
 * 
 * @author robertnorthard
 */
public class BookingDao extends JpaEntityDaoImpl<Long,Booking> {

    /**
     * Return a collection of bookings in the specified state.
     * 
     * @param state state of taxi.
     * @return a list of all bookings in the specified state. A collection with 
     * 0 elements is returned if there is no bookings making the provided state
     */
    public List<Booking> findBookingInState(BookingState state) {

        EntityManager em = this.getEntityManager();
        Query query = em.createNamedQuery("Booking.findBookingsInState", Booking.class);
        query.setParameter("state", state);
        return query.getResultList();
    }
    
    /**
     * Return a collections of bookings corresponding to the provided user.
     * 
     * @param username username of passenger.
     * @return a collections of bookings corresponding to the provided user.
     */
    public List<Booking> findBookingsForPassenger(String username){
        EntityManager em = this.getEntityManager();
        Query query = em.createNamedQuery("Booking.findBookingsForPassenger", Booking.class);
        query.setParameter("username", username);
        return query.getResultList(); 
    }
    
    /**
     * Return a collections of bookings corresponding to the provided driver.
     * 
     * @param username username of driver.
     * @return a collections of bookings corresponding to the provided driver.
     */
    public List<Booking> findBookingsForDriver(String username){
        EntityManager em = this.getEntityManager();
        Query query = em.createNamedQuery("Booking.findBookingsForDriver", Booking.class);
        query.setParameter("username", username);
        return query.getResultList(); 
    }
    
    /**
     * Return a collection of in complete bookings for the specified user.
     * 
     * @param username the user's username.
     * @return a collection of in complete bookings for the specified user.
     */
    public List<Booking> findInCompletedBookingsForUser(String username){
        EntityManager em = this.getEntityManager();
        Query query = em.createNamedQuery("Booking.findBookingsforUserInState", Booking.class);
        query.setParameter("username", username);
        query.setParameter("state", Booking.getCompletedTaxiBookingState());
        return query.getResultList(); 
    }
}
