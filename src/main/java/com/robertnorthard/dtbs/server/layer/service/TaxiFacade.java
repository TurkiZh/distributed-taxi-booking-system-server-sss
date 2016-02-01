package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtms.server.common.model.Taxi;


/**
 * An interface for defining and enforcing operations needed for 
 * the Taxi Service class. It provides the scope of possible 
 * database requests made through the TaxiDAO.
 * @author robertnorthard
 */
public interface TaxiFacade{
    
    /**
     * Find taxi by id.
     * @param id id of taxi.
     * @return taxi or null if not found.
     */
    public Taxi findTaxi(Long id);
    
    /**
     * Update taxi.
     * @param taxi taxi to update.
     */
    public void updateTaxi(Taxi taxi);
}
