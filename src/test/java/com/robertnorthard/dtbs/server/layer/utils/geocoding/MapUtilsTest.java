package com.robertnorthard.dtbs.server.layer.utils.geocoding;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test for MapUtils.
 * @author robertnorthard
 */
public class MapUtilsTest {

    @Test
    public void validateCoordinates() {
        
        //boundary
        assertTrue(MapUtils.validateCoordinates(90,179));
        assertTrue(MapUtils.validateCoordinates(-90,179));
        assertTrue(MapUtils.validateCoordinates(80,180));
        assertTrue(MapUtils.validateCoordinates(90,-180));
        
        //valid
        assertTrue(MapUtils.validateCoordinates(65,70));
        
        //invalid
        assertFalse(MapUtils.validateCoordinates(91,179));
        assertFalse(MapUtils.validateCoordinates(-91,179));
        assertFalse(MapUtils.validateCoordinates(90,181));
        assertFalse(MapUtils.validateCoordinates(90,-181));
    }
}
