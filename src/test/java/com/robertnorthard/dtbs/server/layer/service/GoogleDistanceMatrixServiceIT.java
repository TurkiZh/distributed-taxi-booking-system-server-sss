package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.InvalidGoogleApiResponseException;
import com.robertnorthard.dtbs.server.common.exceptions.RouteNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Location;
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
    
    /**
     * Test of getGeocode method, of class GoogleDistanceMatrixService where address is found.
     */
    @Test
    public void testGetGeocodeWithAddressFound(){
        
        String address= "";
        String expected = "A1(M), Hatfield, Hertfordshire, UK";
        try {
            address = this.googleDistanceMatrixService.getGeocode(51.7531443, -0.244641);
        } catch (InvalidGoogleApiResponseException ex) {
            fail();
        }
        
        assertEquals(address,expected);
    }
       
    /**
     * Test of getGeocode method, of class GoogleDistanceMatrixService where address is not found.
     */
    @Test
    public void testGetGeocodeAddressWithoutAddress(){
        String address = "";
        try {
           address = this.googleDistanceMatrixService.getGeocode(0, 0);
        } catch (InvalidGoogleApiResponseException ex) {
            fail(ex.getMessage());
        }
        
        assertTrue(address == null);
    }
    
   /**
     * Test of getGeocode method, of class GoogleDistanceMatrixService for invalid location.
     */
    @Test
    public void testGetGeocodeLatLngFromAddress(){
        Location location = null;
        // rounded due to varience by Google API in lat/lng reverse lookup.
        Location expectedResult = new Location(51.75, -0.24);
        try {
           location = this.googleDistanceMatrixService
                   .getGeocode(
                           "University of Hertfordshire Main Building, College Ln, Hatfield, Hertfordshire AL10 9AB, UK");
        } catch (InvalidGoogleApiResponseException ex) {
            fail(ex.getMessage());
        }
        
        assertEquals(expectedResult, new Location(
                this.roundToTwoDecimalPlaces(location.getLatitude()),
                this.roundToTwoDecimalPlaces(location.getLongitude())));
    }
  
    
    
    /** 
     * Round to two decimal places.
     * @param value value to round.
     * @return the rounded value.
     */
    private double roundToTwoDecimalPlaces(double value){
        return Math.round(value*100.0)/100.0;
    }
}
