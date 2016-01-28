package com.robertnorthard.dtbs.server.layer.services;

import com.robertnorthard.dtbs.server.configuration.ConfigService;
import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.utils.geocoding.PolyLineUtils;
import com.robertnorthard.dtbs.server.layer.utils.http.HttpUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Google distance matrix service implementation. 
 * @author robertnorthard 
 */
public class GoogleDistanceMatrixServiceImpl implements GoogleDistanceMatrixService {

    private static final Logger LOGGER = Logger.getLogger(GoogleDistanceMatrixServiceImpl.class.getName());
    
    private final Properties properties;
    
    public GoogleDistanceMatrixServiceImpl(){
        this.properties = ConfigService.getConfig("application.properties");
    }
    
    /** 
     * Example:
     * https://maps.googleapis.com/maps/api/distancematrix/json?origins=30%20Cheviots%20hatfield&destinations=welwyn
     * @param origin origin to search from
     * @param destination destination
     * @throws JSONException unable to parse response JSON.
     *
     */
    @Override
    public void getDistance(String origin, String destination) throws JSONException {
       
        String query = this.properties.getProperty("google.distancematrix.api.endpoint") 
                + "/json?origins=" 
                + HttpUtils.stringEncode(origin)
                + "&destinations=" 
                + HttpUtils.stringEncode(destination)
                + "&units=imperial";  
        
        LOGGER.log(Level.INFO, query, "Constructed Query");
        
        JSONObject json = HttpUtils.getUrl(query);
        
        LOGGER.log(Level.INFO, json.toString(), "Result");
        
        LOGGER.log(Level.INFO, json.getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0)
                .getJSONObject("distance")
                .getString("text"), "");
        
    }
    
    /**
     * Get routes from valid Google Directions API response.
     * Pre-condition: response contain a valid route e.g. API response status is OK.
     * Example JSON: https://maps.googleapis.com/maps/api/directions/json?origin=place_id:ChIJ685WIFYViEgRHlHvBbiD5nE&destination=place_id:ChIJA01I-8YVhkgRGJb0fW4UX7Y&key=AIzaSyDY6IVa8pcYJD4lMDbFaCayQr6327cyqMc
     * @param json json to parse.
     * @return a collection of routes.
     */
    @Override
    public List<List<Location>> getRoutes(JSONObject json){
        
        List<List<Location>> routesList = new ArrayList<>();
        JSONArray routes = null;
        JSONArray legs = null;
        JSONArray steps = null;
 
        try {
 
            routes = json.getJSONArray("routes");
 
            for(int i=0;i<routes.length();i++){
                legs = ((JSONObject)routes.get(i)).getJSONArray("legs");
                List<Location> path = new ArrayList<>();
 
                for(int j=0;j<legs.length();j++){
                    steps = ((JSONObject)legs.get(j)).getJSONArray("steps");
 
                    for(int k=0;k<steps.length();k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)steps.get(k)).get("polyline")).get("points");
                        List<Location> list = PolyLineUtils.decodePoly(polyline);
                        
                        for(Location location: list){
                            path.add(location);
                        }
                    }
                }
                routesList.add(path);
            }
        } catch (JSONException e) {
            LOGGER.log(Level.SEVERE, null,e);
        }
        return routesList;
    }
}
