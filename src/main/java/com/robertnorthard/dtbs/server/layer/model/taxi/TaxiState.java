package com.robertnorthard.dtbs.server.layer.model;

import com.robertnorthard.dtbs.server.layer.model.booking.Booking;

/**
 * Abstract class representing a state taxi can be in and common operations.
 * State design pattern. A behavioral pattern that provide a flexible
 * alternative to subclasses and complex difficult to test conditional
 * statements.
 *
 * @author robertnorthard
 */
public abstract class TaxiState {

    private Taxi taxi;

    public TaxiState(Taxi taxi) {
        this.taxi = taxi;
    }

    public abstract void logout();

    public abstract void arriveAtPickPoint();

    public abstract void arrivedAtDestination();

    public abstract void acceptJob(Booking booking);

}
