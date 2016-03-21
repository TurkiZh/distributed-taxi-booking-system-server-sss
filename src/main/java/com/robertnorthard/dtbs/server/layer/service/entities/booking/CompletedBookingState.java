package com.robertnorthard.dtbs.server.layer.service.entities.booking;

import com.robertnorthard.dtbs.server.layer.service.entities.taxi.Taxi;
import java.util.Date;

/**
 * Represents completed booking state.
 *
 * @author robertnorthard
 */
public class CompletedBookingState implements BookingState {

    private IllegalStateException e = new IllegalStateException("Booking completed.");

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
        return "Completed booking state.";
    }
}
