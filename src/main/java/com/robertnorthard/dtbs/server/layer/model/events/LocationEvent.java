package com.robertnorthard.dtbs.server.layer.model.events;

import com.robertnorthard.dtbs.server.layer.model.Location;
import java.io.Serializable;

/**
 * Represents a taxi location change event.
 * @author robertnorthard
 */
public class LocationEvent extends Event implements Serializable{

    private Location location;
    private long taxiId;

    public LocationEvent() {
        // Empty constructor required by JPA.
    }
    
    public LocationEvent(long taxiId, Location location){
        super();
        this.location = location;
        this.taxiId = taxiId;
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
    public long getTaxiId() {
        return taxiId;
    }

    /**
     * @param taxi the taxi to set
     */
    public void setTaxiId(long taxiId) {
        this.taxiId = taxiId;
    }
}
