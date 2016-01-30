package com.robertnorthard.dtbs.server.layer.utils.geocoding;

/**
 * Singleton class for map related utilities.
 * @author robertnorthard
 */
public class MapUtils {
    
    private static MapUtils singleton;
    
    /**
     * private constructor as singleton class.
     */
    private MapUtils() { }
    
    public static MapUtils getInstance(){
        if(singleton == null){
            synchronized(MapUtils.class){
                MapUtils.singleton = new MapUtils();
            }
        }
        
        return MapUtils.singleton;
    }
    
    /**
     * Validates provided latitude and longitude.
     * @param latitude
     * @param longitude
     * @return true if valid else false.
     */
    public boolean validateCoordinates(double latitude, double longitude){
        
        if(latitude > 90 || latitude < -90){
            return false;
            
        }else if(longitude < -180 || longitude > 180){
            return false;
        }
        
        return true;
    }
    
}
