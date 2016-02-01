package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.configuration.ConfigService;
import com.robertnorthard.dtbs.server.layer.utils.geocoding.PolyLineUtils;
import com.robertnorthard.dtbs.server.layer.utils.http.HttpUtils;
import com.robertnorthard.dtms.server.common.model.Location;
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
 *
 * @author robertnorthard
 */
public class GoogleDistanceMatrixService implements GoogleDistanceMatrixFacade {

    private static final Logger LOGGER = Logger.getLogger(GoogleDistanceMatrixService.class.getName());

    private final Properties properties;

    public GoogleDistanceMatrixService() {
        this.properties = ConfigService.getConfig("application.properties");
    }

    /**
     * Example:
     * https://maps.googleapis.com/maps/api/distancematrix/json?origins=30%20Cheviots%20hatfield&destinations=welwyn
     *
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
     * Perform a Geocode reverse lookup using latitude and longitude.
     * Query example: http://maps.googleapis.com/maps/api/geocode/json?latlng=44.4647452,7.3553838
     * @param latitude
     */
    @Override
    public String getGeocode(double latitude, double longitude) 
            throws JSONException{
        
        String query = this.properties.getProperty("google.geocoding.api.endpoint")
                + "/json?latlng=" + latitude + "," + longitude;

        LOGGER.log(Level.INFO, "getGeocode query - {0}", query);
        
        JSONObject json = HttpUtils.getUrl(query);

        LOGGER.log(Level.INFO, "getGeocode - {0}", json);
        
        if (!"ZERO_RESULTS".equals(json.getString("status"))) {
            JSONArray results = json.getJSONArray("results");
            JSONObject object = results.getJSONObject(0);

            return object.getString("formatted_address");
        }else{
            return null;
        }
    }

    /**
     * Use Google Geocoding API to determine coordinates and formatted address.
     *
     * Example query:
     * http://maps.googleapis.com/maps/api/geocode/json?address=%22po5%201pl%22
     *
     * @param address address to lookup.
     * @return
     * @throws JSONException JSON encoding error.
     */
    @Override
    public Location getGeocode(String address) throws JSONException {

        String query = this.properties.getProperty("google.geocoding.api.endpoint")
                + "/json?address=" + HttpUtils.stringEncode(address);

        JSONObject json = HttpUtils.getUrl(query);

        if (!"ZERO_RESULTS".equals(json.getString("status"))) {
            JSONArray results = json.getJSONArray("results");
            JSONObject object = results.getJSONObject(0);

            JSONObject location = object.getJSONObject("geometry").getJSONObject("location");

            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            
            return new Location(lat,lng);
        }else{
            return null;
        }
    }

    /**
     * Get routes from valid Google Directions API response. 
     * Pre-condition:
     * response contain a valid route e.g. API response status is OK. Example
     * 
     * Example query:
     * https://maps.googleapis.com/maps/api/directions/json?origin=place_id:ChIJ685WIFYViEgRHlHvBbiD5nE&destination=place_id:ChIJA01I-8YVhkgRGJb0fW4UX7Y&key=AIzaSyDY6IVa8pcYJD4lMDbFaCayQr6327cyqMc
     *
     * @param json json to parse.
     * @return a collection of routes.
     */
    @Override
    public List<List<Location>> getRoutes(JSONObject json) {

        List<List<Location>> routesList = new ArrayList<>();
        JSONArray routes = null;
        JSONArray legs = null;
        JSONArray steps = null;

        try {

            routes = json.getJSONArray("routes");

            for (int i = 0; i < routes.length(); i++) {
                legs = ((JSONObject) routes.get(i)).getJSONArray("legs");
                List<Location> path = new ArrayList<>();

                for (int j = 0; j < legs.length(); j++) {
                    steps = ((JSONObject) legs.get(j)).getJSONArray("steps");

                    for (int k = 0; k < steps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) steps.get(k)).get("polyline")).get("points");
                        List<Location> list = PolyLineUtils.decodePoly(polyline);

                        for (Location location : list) {
                            path.add(location);
                        }
                    }
                }
                routesList.add(path);
            }
        } catch (JSONException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return routesList;
    }
}
