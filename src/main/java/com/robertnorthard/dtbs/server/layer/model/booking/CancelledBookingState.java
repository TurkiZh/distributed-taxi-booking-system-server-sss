package com.robertnorthard.dtbs.server.layer.model.booking;

import com.robertnorthard.dtbs.server.layer.model.taxi.Taxi;
import java.util.Date;

/**
 * Represents the canceled taxi booking state.
 *
 * @author robertnorthard
 */
public class CancelledBookingState implements BookingState {

    private IllegalStateException e = new IllegalStateException("Booking already canceled.");

    @Override
    public void cancelBooking(Booking booking) {
        throw e;
    }

    @Override
    public void cancelTaxi(Booking booking) {
        throw e;
    }

    @Override
    public void dispatchTaxi(Booking booking, Taxi taxi) {
        throw e;
    }

    @Override
    public void dropOffPassenger(Booking booking, Date time) {
        throw e;
    }

    @Override
    public void pickupPassenger(Booking booking, Date time) {
        throw e;
    }

    @Override
    public String toString() {
        return "Booking canceled state.";
    }
}
