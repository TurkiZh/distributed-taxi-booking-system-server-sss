package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.TaxiNotFoundException;
import javax.ejb.Remote;

/**
 * An interface for defining and enforcing operations needed for the 
 * location tracking of taxis.
 *
 * @author robertnorthard
 */
@Remote
public interface LocationTrackingFacade {

    /**
     * Update taxi location by id.
     *
     * @param id the id of the taxi.
     * @param latitude Taxi's current latitude.
     * @param longitude Taxi's current longitude.
     * @param timestamp timestamp of event.
     * @throws TaxiNotFoundException taxi not found.
     */
    public void updateLocation(Long id, double latitude, double longitude, long timestamp)
            throws TaxiNotFoundException;
}
