package com.robertnorthard.dtbs.server.layer.model.taxi;

/**
 *
 * @author robertnorthard
 */
public class AcceptedJobTaxiState implements TaxiState {

    @Override
    public void goOffDuty(Taxi taxi) {
        throw new IllegalStateException("Cango off duty after accepting a booking.");
    }

    @Override
    public void acceptJob(Taxi taxi) {
        throw new IllegalStateException("Already accepting booking");
    }

    @Override
    public void goOnDuty(Taxi taxi) {
        throw new IllegalStateException("Already on duty.");
    }
    
    @Override
    public void completeJob(Taxi taxi) {
        taxi.setState(Taxi.getAcceptedJobTaxiState());
    } 
    
    @Override
    public String toString(){
        return "On job.";
    }
}
