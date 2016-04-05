package com.robertnorthard.dtbs.server.layer.controllers;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

/**
 *
 * @author robertnorthard
 */
public class BookingControllerIT {
    
    /**
     * Test method makeBooking of BookingController. 
     * Test: unauthorised no credentials.
     */
    @Test
    public void testMakeBookingUnathorisedNoCredentials() {
        expect()
                .statusCode(403)
                .request()
                .header("Content-Type", "application/json")
                .body("{\"end_location\":{\"latitude\":51.763366,\"longitude\":-0.22309},\"start_location\":{{\"latitude\":51.763366,\"longitude\":-0.22309},\"number_passengers\":1}")
                .and()
                .response()
                .when()
                .post("http://127.0.0.1:8080/api/v1/booking");
    }
    
    /**
     * Test method makeBooking of BookingController. 
     * Test: unauthorised as driver. Passengers should only be allowed to make taxi bookings.
     */
    @Test
    public void testMakeBookingUnathorisedAsDriver() {
        expect()
                .statusCode(403)
                .request()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic am9obi5zbWl0aDpwYXNzd29yZA==")
                .body("{\"end_location\":{\"latitude\":51.763366,\"longitude\":-0.22309},\"start_location\":{{\"latitude\":51.763366,\"longitude\":-0.22309},\"number_passengers\":1}")
                .and()
                .response()
                .when()
                .post("http://127.0.0.1:8080/api/v1/booking");
    }
    
        /**
     * Test method makeBooking of BookingController. 
     * Test: same start and destination location.
     */
    @Test
    public void testMakeBookingBookingWithin200metres() {
        expect()
                .statusCode(400)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .header("Content-Type", "application/json")
                .body("{\"end_location\":{\"latitude\":51.75093,\"longitude\":-0.23393},\"start_location\":{{\"latitude\":51.7507,\"longitude\":-0.23393},\"number_passengers\":1}")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://127.0.0.1:8080/api/v1/booking");
    }
    
}