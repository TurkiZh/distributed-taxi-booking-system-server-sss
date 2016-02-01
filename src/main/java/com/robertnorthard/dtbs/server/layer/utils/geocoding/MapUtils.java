package com.robertnorthard.dtbs.server.layer.utils.geocoding;

/**
 * Singleton class for map related utilities.
 * @author robertnorthard
 */
public class MapUtils {
    
    private MapUtils(){
        // Empty as utility class.
    }
    
    /**
     * Validates provided latitude and longitude.
     * @param latitude
     * @param longitude
     * @return true if valid else false.
     */
    public static boolean validateCoordinates(double latitude, double longitude){
        
        if(latitude > 90 || latitude < -90){
            return false;
            
        }else if(longitude < -180 || longitude > 180){
            return false;
        }
        
        return true;
    }
    
}
