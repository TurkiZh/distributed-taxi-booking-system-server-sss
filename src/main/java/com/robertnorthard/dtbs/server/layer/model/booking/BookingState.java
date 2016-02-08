package com.robertnorthard.dtbs.server.layer.model.booking;

import com.robertnorthard.dtbs.server.layer.model.Taxi;
import java.util.Date;

/**
 * Interface representing a booking state.
 *
 * @author robertnorthard
 */
public abstract class BookingState {

    /**
     * Cancel booking.
     *
     * @param booking booking callback.
     */
    public abstract void cancelBooking(Booking booking);

    /**
     * Cancel taxi for booking.
     *
     * @param booking booking callback.
     */
    public abstract void cancelTaxi(Booking booking);

    /**
     * Dispatch taxi.
     *
     * @param booking booking callback.
     * @param taxi taxi taking booking.
     */
    public abstract void dispatchTaxi(Booking booking, Taxi taxi);

    /**
     * Drop off passenger.
     *
     * @param booking booking callback.
     * @param time time passenger arrived at their destination.
     * @throws IllegalArgumentException Invalid time. Start time cannot be
     * before booking creation timestamp.
     */
    public abstract void dropOffPassenger(Booking booking, Date time);

    /**
     * Pickup passenger.
     *
     * @param booking booking callback
     * @param time time passenger picked up.
     * @throws IllegalArgumentException Invalid time. Start time cannot be
     * before booking creation timestamp.
     */
    public abstract void pickupPassenger(Booking booking, Date time);
}
