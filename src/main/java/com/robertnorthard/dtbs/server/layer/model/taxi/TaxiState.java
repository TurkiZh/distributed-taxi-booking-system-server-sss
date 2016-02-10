package com.robertnorthard.dtbs.server.layer.model.taxi;

/**
 * Interface representing a state a taxi can be in and common operations.
 * State design pattern. A behavioral pattern that provide a flexible
 * alternative to subclasses and complex difficult to test conditional
 * statements.
 *
 * @author robertnorthard
 */
public interface TaxiState {

    public void goOffDuty(Taxi taxi);

    public void goOnDuty(Taxi taxi);

    public void acceptJob(Taxi taxi);
    
    public void completeJob(Taxi taxi);
}
