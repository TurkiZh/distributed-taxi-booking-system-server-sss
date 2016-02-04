package com.robertnorthard.dtbs.server.layer.model.events;

import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import java.io.Serializable;

/**
 * Represents a taxi location change event.
 * @author robertnorthard
 */
public class LocationEvent extends Event implements Serializable{

    private Location location;
    private Taxi taxi;

    public LocationEvent() {
        // Empty constructor required by JPA.
    }
    
    public LocationEvent(Taxi taxi, Location location){
        super();
        this.location = location;
        this.taxi = taxi;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the taxi
     */
    public Taxi getTaxi() {
        return taxi;
    }

    /**
     * @param taxi the taxi to set
     */
    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }
}
