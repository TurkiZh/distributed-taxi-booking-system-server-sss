package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtms.server.common.model.Location;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Service class for Google's distance matrix and geocode API.
 * @author robertnorthard
 */
public interface GoogleDistanceMatrixFacade {
    
    /**
     * Return distance between origin and destination.
     * @param origin
     * @param destination
     * @throws JSONException 
     */
    public void getDistance(String origin, String destination) 
            throws JSONException;
    
    public List<List<Location>> getRoutes(JSONObject json);
    
    public Location getGeocode(String address) throws JSONException;
    
    public String getGeocode(double latitude, double longitude) throws JSONException;
}
