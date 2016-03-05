package com.robertnorthard.dtbs.server.layer.controllers;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

/**
 * Integration tests for Geocode controller.
 *
 * @author robertnorthard
 */
public class GeocodeControllerIT {

    public GeocodeControllerIT() {
    }

    /**
     * Test method reverse of GeocodeController. 
     * Test: valid coordinates.
     */
    @Test
    public void testReverseValidCoordinates() {
        expect()
                .statusCode(200)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .and()
                .response()
                .body("data", equalTo("Hatfield AL10 9AB, UK"))
                .when()
                .get(" http://127.0.0.1:8080/api/v1/geocode/reverse?latitude=51.753306&longitude=-0.241096");
    }

    /**
     * Test method reverse of GeocodeController. 
     * Test: valid coordinates when
     * unauthorised.
     */
    @Test
    public void testReverseUnauthorised() {
        expect()
                .statusCode(403)
                .when()
                .get(" http://127.0.0.1:8080/api/v1/geocode/reverse?latitude=51.753306&longitude=-0.241096");
    }

    /**
     * Test method reverse of GeocodeController. 
     * Test: missing latitude.
     */
    @Test
    public void testReverseMissingLatitiude() {
        expect()
                .statusCode(400)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .when()
                .get(" http://127.0.0.1:8080/api/v1/geocode/reverse?longitude=-0.241096");
    }
    
   /**
     * Test method reverse of GeocodeController. 
     * Test: missing longitude.
     */
    @Test
    public void testReverseMissingLongitude() {
        expect()
                .statusCode(400)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .when()
                .get("http://127.0.0.1:8080/api/v1/geocode/reverse?latitude=-0.2s");
    }

    /**
     * Test method lookup of GeocodeController. 
     * Test: valid address.
     */
    @Test
    public void testLookupValidAddress() {
        expect()
                .statusCode(200)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .when()
                .get("http://127.0.0.1:8080/api/v1/geocode/address/lookup?address=30 cheviots");
    }
    
    /**
     * Test method lookup of GeocodeController. 
     * Test: invalid address.
     */
    @Test
    public void testLookupInvalidAdddress() {
        expect()
                .statusCode(404)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .when()
                .get("http://127.0.0.1:8080/api/v1/geocode/address/lookup?address=non exisitence");
    }
    
    /**
     * Test method lookup of GeocodeController. 
     * Test: unauthorised.
     */
    @Test
    public void testLookupUnAuthorised() {
        expect()
                .statusCode(403)
                .when()
                .get("http://127.0.0.1:8080/api/v1/geocode/address/lookup?address=non exisitence");
    } 
    
    /**
     * Test method estimateTravelTime of GeocodeController. 
     * Test:  unauthorised.
     */
    @Test
    public void testEstimateTravelTimeUnauthoirsed() {
        expect()
                .statusCode(403)
                .when()
                .get("http://localhost:8080/api/v1/geocode/route/estimate/time?start_latitude=51.750696&start_longitude=-0.233978&end_latitude=51.758400&end_longitude=-0.238785");
    } 
    
    /**
     * Test method estimateTravelTi,e of GeocodeController. 
     * Test: valid start and end coordinates.
     */
    @Test
    public void testEstimateTravelTimeValidCoordinates() {
        expect()
                .statusCode(200)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .and()
                .response()
                .body("status", equalTo("0"))
                .when()
                .get("http://localhost:8080/api/v1/geocode/route/estimate/time?start_latitude=51.750696&start_longitude=-0.233978&end_latitude=51.758400&end_longitude=-0.238785");
    } 
    
    /**
     * Test method estimateTravelTi,e of GeocodeController. 
     * Test: missing start coordinates.
     */
    @Test
    public void testEstimateTravelTimeMissingStartCoordinates() {
        expect()
                .statusCode(400)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .get("http://localhost:8080/api/v1/geocode/route/estimate/time?end_latitude=51.758400&end_longitude=-0.238785");
    } 
    
    /**
     * Test method estimateTravelTi,e of GeocodeController. 
     * Test: missing destination coordinates.
     */
    @Test
    public void testEstimateTravelTimeMissingEndCoordinates() {
        expect()
                .statusCode(400)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .get("http://localhost:8080/api/v1/geocode/route/estimate/time?start_latitude=51.758400&start_longitude=-0.238785");
    } 
}
