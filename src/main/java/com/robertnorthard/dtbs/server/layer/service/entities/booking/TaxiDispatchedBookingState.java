package com.robertnorthard.dtbs.server.layer.service.entities.booking;

import com.robertnorthard.dtbs.server.layer.service.entities.taxi.Taxi;
import java.util.Date;

/**
 * Taxi dispatched booking state.
 *
 * @author robertnorthard
 */
public class TaxiDispatchedBookingState implements BookingState {

    @Override
    public void cancelBooking(Booking booking) {
        booking.setState(
                Booking.getCancelledBookingState());
    }

    @Override
    public void cancelTaxi(Booking booking) {
        booking.setTaxi(null);

        booking.setState(
                Booking.getAwaitingTaxiBookingState());
    }

    @Override
    public void dispatchTaxi(Booking booking, Taxi taxi) {
        throw new IllegalStateException("Taxi already dispatched.");
    }

    @Override
    public void dropOffPassenger(Booking booking, Date time) {
        throw new IllegalStateException("Passenger not picked up.");
    }

    @Override
    public void pickupPassenger(Booking booking, Date time) {

        if (time.after(booking.getTimestamp())) {
            booking.setStartTime(time);

            booking.setState(
                    Booking.getPassengerPickedUpBookingState());
        } else {
            throw new IllegalArgumentException("Time must be after initial booking creation time");
        }
    }

    @Override
    public String toString() {
        return "Taxi dispatched state.";
    }
}
