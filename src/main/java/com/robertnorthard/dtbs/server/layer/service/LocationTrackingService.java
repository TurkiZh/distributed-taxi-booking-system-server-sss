package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.dto.LocationDto;
import com.robertnorthard.dtbs.server.common.dto.TaxiLocationEventDto;
import com.robertnorthard.dtbs.server.common.exceptions.TaxiNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.model.taxi.Taxi;
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
     * @throws TaxiNotFoundException taxi not found.
     * @IllegalArgumentException invalid latitude or longitude.
     */
    @Override
    public synchronized void updateLocation(Long id, double latitude, double longitude, long timestamp)
            throws TaxiNotFoundException {

        Location lastKnownLocation;

        try {
            lastKnownLocation = new Location(latitude, longitude);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }

        Taxi taxi = this.taxiService.findTaxi(id);

        if (taxi == null) {
            throw new TaxiNotFoundException();
        }

        Location previousLocation = taxi.getLocation();
        taxi.updateLocation(lastKnownLocation);

        if (previousLocation == null) {
            this.locationDao.persistEntity(lastKnownLocation);
        }

        this.taxiService.updateTaxi(taxi);

        // create taxi location event.
        TaxiLocationEventDto event = new TaxiLocationEventDto(id, taxi.getState().toString(), 
                new LocationDto(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), timestamp);

        //notify all taxi subscribers of updated taxi location
        this.notifyObservers(event);
    }
}
