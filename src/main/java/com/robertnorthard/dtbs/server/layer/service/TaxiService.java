package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.layer.model.Taxi;
import com.robertnorthard.dtbs.server.layer.persistence.TaxiDao;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Taxi Service class implementation.
 * 
 * @author robertnorthard
 */
public class TaxiService implements TaxiFacade {
    
    private Map<Integer, Taxi> allTaxisCache = new ConcurrentHashMap<>();
    private TaxiDao taxiDao = new TaxiDao();
    
    public TaxiService(){
        // left blank. 
    }
    
    /**
     * Constructor - used for dependency injection.
     * @param taxoDao taxi dao.
     */
    public TaxiService(TaxiDao taxoDao){
        this.taxiDao = taxoDao;
    }

    /**
     * Find taxi by id.
     * @param id id of taxi.
     * @return taxi or null if not found.
     */
    @Override
    public Taxi findTaxi(Long id) {
        return this.taxiDao.findEntityById(id);
    }

   /**
     * Update taxi.
     * @param taxi taxi to update.
     */
    @Override
    public void updateTaxi(Taxi taxi) {
        this.taxiDao.update(taxi);
    }
}
