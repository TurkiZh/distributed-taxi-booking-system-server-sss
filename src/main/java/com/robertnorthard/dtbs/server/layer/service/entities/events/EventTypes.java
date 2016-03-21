package com.robertnorthard.dtbs.server.layer.service.entities.events;

/**
 * Sore event type constants.
 * 
 * @author robertnorthard
 */
public enum EventTypes {
    BOOKING_EVENT("bookingEvent");
    
    private final String description;
    
    /**
     * Default constructor.
     * @param description description of event.
     */
    EventTypes(String description){
        this.description = description;
    }
    
    /**
     * @return a string representation of an event. 
     */
    @Override
    public String toString(){
        return this.description;
    }
}
