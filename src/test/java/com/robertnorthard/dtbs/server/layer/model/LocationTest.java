package com.robertnorthard.dtbs.server.layer.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Location class.
 * @author robertnorthard
 */
public class LocationTest {
    
    @Test
    public void validLocation() {
        Location location = new Location(90,180);
        assertTrue(location.getLatitude() == 90);
        assertTrue(location.getLongitude() == 180);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void invalidLatititudeLocation() {
        Location location = new Location(91,180);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void invalidLongitudeLocation() {
        Location location = new Location(90,182);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void invalidLatititudeLongitudeLocation() {
        Location location = new Location(91,181);
    }
}
