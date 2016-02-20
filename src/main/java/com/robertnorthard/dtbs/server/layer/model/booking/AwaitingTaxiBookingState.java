package com.robertnorthard.dtbs.server.layer.model.booking;

import com.robertnorthard.dtbs.server.layer.model.taxi.Taxi;
import java.util.Date;

/**
 * Represents the awaiting taxi booking state.
 *
 * @author robertnorthard
 */
public class AwaitingTaxiBookingState implements BookingState {

    @Override
    public void cancelBooking(Booking booking) {
        booking.setState(
                Booking.getCancelledBookingState());
    }

    @Override
    public void cancelTaxi(Booking booking) {
        throw new IllegalStateException("Taxi not dispatched.");
    }

    @Override
    public void dispatchTaxi(Booking booking, Taxi taxi) {

        booking.setState(
                Booking.getTaxiDispatchedBookingState());

        if (!taxi.checkseatAvailability(booking.getNumberPassengers())) {
            throw new IllegalStateException("The taxi does not have enough seats.");
        }

        booking.setTaxi(taxi);
    }

    @Override
    public void dropOffPassenger(Booking booking, Date time) {
        throw new IllegalStateException("Passenger not picked up");
    }

    @Override
    public void pickupPassenger(Booking booking, Date time) {
        throw new IllegalStateException("Taxi not yet dispatched.");
    }

    @Override
    public String toString() {
        return "Awaiting taxi.";
    }
}
