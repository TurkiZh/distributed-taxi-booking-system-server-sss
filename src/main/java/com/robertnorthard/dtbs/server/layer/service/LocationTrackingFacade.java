package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;

/**
 * An interface for defining and enforcing operations needed for the 
 * location tracking of taxis.
 *
 * @author robertnorthard
 */
public interface LocationTrackingFacade {

    /**
     * Update taxi location by id.
     *
     * @param id the id of the taxi.
     * @param latitude Taxi's current latitude.
     * @param longitude Taxi's current longitude.
     * @param timestamp timestamp of event.
     * @throws EntityNotFoundException taxi not found.
     */
    public void updateLocation(Long id, double latitude, double longitude, long timestamp)
            throws EntityNotFoundException;
}
