package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.events.LocationEvent;
import com.robertnorthard.dtbs.server.layer.persistence.LocationDao;
import com.robertnorthard.dtbs.server.layer.utils.Subject;
import com.robertnorthard.dtbs.server.layer.utils.geocoding.MapUtils;
import com.robertnorthard.dtms.server.common.model.Location;
import com.robertnorthard.dtms.server.common.model.Taxi;

/**
 * Location tracking service facade implementation. 
 * @author robertnorthard
 */
public class LocationTrackingService extends Subject implements LocationTrackingFacade {

    private static LocationTrackingService locationTrackingService;
    private final TaxiFacade taxiService;
    private final LocationDao locationDao;
    
    /** 
     * Singleton - private constructor.
     */
    private LocationTrackingService() {
        this.taxiService = new TaxiService();
        this.locationDao = new LocationDao();
    }

    /**
     * Return a shared instance of the TaxiServiceImpl. If null create a new
     * instance.
     *
     * @return a shared instance of the TaxiFacade.
     */
    public static LocationTrackingService getInstance() {

        if (LocationTrackingService.locationTrackingService == null) {
            //dead-locking approach to synchronisation.
            synchronized (LocationTrackingService.class) {
                LocationTrackingService.locationTrackingService = new LocationTrackingService();
            }
        }

        return LocationTrackingService.locationTrackingService;
    }

    /**
     * Update taxi location by id.
     * Find taxi by id and set new location and broadcast update taxi location.
     *
     * @param id the id of the taxi.
     * @param latitude Taxi's current latitude.
     * @param longitude Taxi's current longitude.
     * @param timestamp timestamp of event.
     * @throws EntityNotFoundException taxi not found.
     * @IllegalArgumentException invalid latitude or longitude.
     */
    @Override
    public synchronized void updateLocation(Long id, double latitude, double longitude, long timestamp)
            throws EntityNotFoundException {

        if(!MapUtils.validateCoordinates(latitude, longitude)){
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }
        
        Taxi taxi = this.taxiService.findTaxi(id);
        
        if(taxi == null){
            throw new EntityNotFoundException();
        }

        Location lastKnownLocation = new Location(latitude, longitude);
        Location previousLocation = taxi.getLocation(); 
        taxi.updateLocation(lastKnownLocation);
        
        if(previousLocation == null){
            this.locationDao.persistEntity(lastKnownLocation);
        }
        
        this.taxiService.updateTaxi(taxi);
        
        // create taxi location event.
        LocationEvent event = new LocationEvent(taxi, lastKnownLocation);  
        
        //notify all taxi subscribers of updated taxi location
        this.notifyObservers(event);
    }
}
