package com.robertnorthard.dtbs.server.layer.services;

import com.robertnorthard.dtbs.server.layer.model.Location;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Service class for Google's distance matrix api.
 * @author robertnorthard
 */
public interface GoogleDistanceMatrixService {
    
    public void getDistance(String origin, String destination) 
            throws JSONException;
    
    public List<List<Location>> getRoutes(JSONObject json);
}
