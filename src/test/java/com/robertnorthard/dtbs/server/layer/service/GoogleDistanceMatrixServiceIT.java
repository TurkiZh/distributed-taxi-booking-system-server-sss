package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.InvalidGoogleApiResponseException;
import com.robertnorthard.dtbs.server.common.exceptions.RouteNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.model.Route;
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
     * Test of getDistance method, of class GoogleDistanceMatrixService for same
     * start and end location.
     */
    @Test
    public void testGetDistanceForSameStartEnd() {

        double expected = 0;
        // max different between expected and actual - Google may choose different routes.
        float delta = 0;

        double actual = 0;

        try {
            actual = this.googleDistanceMatrixService
                    .getDistance("Innovation Centre, Hatfield Hertfordshire AL10 9PN, UK", "Innovation Centre, Hatfield Hertfordshire AL10 9PN, UK");
        } catch (InvalidGoogleApiResponseException | RouteNotFoundException ex) {
            fail();
        }

        assertEquals(expected, actual, 0);
    }


    /**
     * Test of getDistance method, of class GoogleDistanceMatrixService where
     * route is not found.
     */
    @Test(expected = RouteNotFoundException.class)
    public void testGetDistanceRouteNotFound() throws Exception {
        String origin = "1234567890";
        String destination = "1234567890";
        this.googleDistanceMatrixService.getDistance(origin, destination);
    }

    /**
     * Test of getGeocode method, of class GoogleDistanceMatrixService where
     * address is found.
     */
    @Test
    public void testGetGeocodeWithAddressFound() {

        String address = "";
        String expected = "A1(M), Hatfield, Hertfordshire, UK";
        try {
            address = this.googleDistanceMatrixService.getGeocode(51.7531443, -0.244641);
        } catch (InvalidGoogleApiResponseException ex) {
            fail();
        }

        assertEquals(address, expected);
    }

    /**
     * Test of getGeocode method, of class GoogleDistanceMatrixService where
     * address is not found.
     */
    @Test
    public void testGetGeocodeAddressWithoutAddress() {
        String address = "";
        try {
            address = this.googleDistanceMatrixService.getGeocode(0, 0);
        } catch (InvalidGoogleApiResponseException ex) {
            fail(ex.getMessage());
        }

        assertTrue(address == null);
    }

    /**
     * Test of getGeocode method, of class GoogleDistanceMatrixService for
     * invalid location.
     */
    @Test
    public void testGetGeocodeLatLngFromAddress() {
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
     * Test of getRouteInfo method, of class GoogleDistanceMatrixService for
     * valid route.
     */
    @Test
    public void testGetRouteInfoValidRoute() {

        Location startLocation = new Location(51.753306, -0.241096);
        Location endLocation = new Location(51.761143, -0.248649);
        // max different between expected and actual - Google may choose different routes.
        float delta = 100;
        Route route = null;

        try {
            route = this.googleDistanceMatrixService.getRouteInfo(startLocation, endLocation);
        } catch (InvalidGoogleApiResponseException ex) {
            fail();
        }

        assertTrue(route.getStartAddress().getAddress().toString().equals("Automotive Centre, Hatfield, Hertfordshire AL10 9PN, UK"));
        assertTrue(route.getEndAddress().getAddress().toString().equals("Sandridge, Hatfield, Hertfordshire AL10 9BL, UK"));
        assertEquals(2855, route.getDistance(), delta);
    }

    /**
     * Test of getRouteInfo method, of class GoogleDistanceMatrixService for no
     * route.
     */
    @Test
    public void testGetRouteInfoNoroute() {

        Location startLocation = new Location(0, 0);
        Location endLocation = new Location(0, 0);
        Route route = null;

        try {
            route = this.googleDistanceMatrixService.getRouteInfo(startLocation, endLocation);
        } catch (InvalidGoogleApiResponseException ex) {
            fail();
        }

        assertTrue(route == null);
    }

    /**
     * Test of getDistance method, of class GoogleDistanceMatrixService for
     * valid route.
     */
    @Test
    public void testGetDistanceForValidRoute() {

        double expected = 2855;
        // max different between expected and actual - Google may choose different routes.
        float delta = 100;

        double actual = 0;

        try {
            actual = this.googleDistanceMatrixService
                    .getDistance("Innovation Centre, Hatfield, "
                            + "Hertfordshire AL10 9PN, UK", "Sandridge, Hatfield, Hertfordshire AL10 9BL, UK");
        } catch (InvalidGoogleApiResponseException | RouteNotFoundException ex) {
            fail();
        }

        assertEquals(expected, actual, 100);
    }

    /**
     * Round to two decimal places.
     *
     * @param value value to round.
     * @return the rounded value.
     */
    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
