package com.robertnorthard.dtbs.server.layer.services;

/**
 * Service class for Taxis.
 * @author robertnorthard
 */
public interface TaxiService {
    
    /**
     * Update taxi location by id.
     * @param id the id of the taxi.
     * @param latitude Taxi's current latitude.
     * @param longitude Taxi's current longitude.
     */
    public void updateLocation(Long id, String latitude, String longitude);
    
}
