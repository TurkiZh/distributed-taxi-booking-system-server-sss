package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.RouteNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Integration tests for Google distance matric service facade. 
 * 
 * @author robertnorthard
 */
public class GoogleDistanceMatrixServiceIT {
    
    private final GoogleDistanceMatrixFacade googleDistanceMatrixService;
    
    public GoogleDistanceMatrixServiceIT() {
        this.googleDistanceMatrixService = new GoogleDistanceMatrixService();
    }
    
    /**
     * Test of getDistance method, of class GoogleDistanceMatrixService.
     */
    @Test
    public void testGetDistance() throws Exception {
        String origin = "82 Cherry Way, Hatfield";
        String destination = " Cheviots, Hatfield";
        assertTrue(this.googleDistanceMatrixService.getDistance(origin, destination) == 563.0);
    }
    
   /**
     * Test of getDistance method, of class GoogleDistanceMatrixService where route is not found.
     */
    @Test(expected = RouteNotFoundException.class)
    public void testGetDistanceRouteNotFound() throws Exception {
        String origin = "1234567890";
        String destination = "1234567890";
        this.googleDistanceMatrixService.getDistance(origin, destination);
    }
}
