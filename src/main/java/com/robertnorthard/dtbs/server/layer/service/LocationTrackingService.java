package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import com.robertnorthard.dtbs.server.layer.model.events.LocationEvent;
import com.robertnorthard.dtbs.server.layer.persistence.LocationDao;
import com.robertnorthard.dtbs.server.layer.utils.Subject;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Location tracking service facade implementation.
 *
 * @author robertnorthard
 */
@Singleton
public class LocationTrackingService extends Subject implements LocationTrackingFacade {

    @Inject
    private TaxiFacade taxiService;
    @Inject
    private LocationDao locationDao;

    /**
     * Update taxi location by id. Find taxi by id and set new location and
     * broadcast update taxi location.
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

        Location lastKnownLocation;

        try {
            lastKnownLocation = new Location(latitude, longitude);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }

        Taxi taxi = this.taxiService.findTaxi(id);

        if (taxi == null) {
            throw new EntityNotFoundException();
        }

        Location previousLocation = taxi.getLocation();
        taxi.updateLocation(lastKnownLocation);

        if (previousLocation == null) {
            this.locationDao.persistEntity(lastKnownLocation);
        }

        this.taxiService.updateTaxi(taxi);

        // create taxi location event.
        LocationEvent event = new LocationEvent(id, lastKnownLocation);

        //notify all taxi subscribers of updated taxi location
        this.notifyObservers(event);
    }
}
