package com.robertnorthard.dtbs.server.layer.utils.geocoding;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test for MapUtils.
 * @author robertnorthard
 */
public class MapUtilsTest {
    
    private final MapUtils mapUtils;
    
    public MapUtilsTest() {
        this.mapUtils = MapUtils.getInstance();
    }
    
    @Test
    public void validateCoordinates() {
        
        //boundary
        assertTrue(this.mapUtils.validateCoordinates(90,179));
        assertTrue(this.mapUtils.validateCoordinates(-90,179));
        assertTrue(this.mapUtils.validateCoordinates(80,180));
        assertTrue(this.mapUtils.validateCoordinates(90,-180));
        
        //valid
        assertTrue(this.mapUtils.validateCoordinates(65,70));
        
        //invalid
        assertFalse(this.mapUtils.validateCoordinates(91,179));
        assertFalse(this.mapUtils.validateCoordinates(-91,179));
        assertFalse(this.mapUtils.validateCoordinates(90,181));
        assertFalse(this.mapUtils.validateCoordinates(90,-181));
    }
}
