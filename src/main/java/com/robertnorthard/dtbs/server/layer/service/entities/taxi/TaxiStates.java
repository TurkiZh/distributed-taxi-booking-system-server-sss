package com.robertnorthard.dtbs.server.layer.service.entities.taxi;

/**
 * Represents taxi states.
 * @author robertnorthard
 */
public enum TaxiStates {
    OFF_DUTY("OFF_DUTY"),
    ON_DUTY("ON_DUTY"),
    ON_JOB("ON_JOB");
    
    private String textualDescription;
    
    /**
     * Default constructor.
     * 
     * @param description string representation of a taxi state.
     */
    TaxiStates(String description){
        this.textualDescription = description;
    }
    
    @Override
    public String toString(){
        return this.textualDescription;
    }
}
