package com.robertnorthard.dtbs.server.layer.model.events;

import com.robertnorthard.dtbs.server.layer.model.Location;
import java.io.Serializable;

/**
 * Represents a taxi location change event.
 *
 * @author robertnorthard
 */
public class LocationEvent extends Event implements Serializable {

    private long taxiId;
    private Location location;

    public LocationEvent() {
        // Empty constructor required by JPA.
    }

    /**
     * Constructor for class LocationEvent.
     * @param taxiId taxi id.
     * @param location location.
     */
    public LocationEvent(long taxiId, Location location) {
        super();
        this.location = location;
        this.taxiId = taxiId;
    }
    
    /**
     * Constructor for class LocationEvent.
     * @param taxiId taxi id.
     * @param location location for event.
     * @param timestamp timestamp of event.
     */
    public LocationEvent(long taxiId, Location location, long timestamp) {
        super(timestamp);
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
