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
     * Test: valid booking.
     */
    @Test
    public void testMakeBookingValidBooking() {
        expect()
                .statusCode(200)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .header("Content-Type", "application/json")
                .body("{\"end_location\":{\"latitude\":51.763366,\"longitude\":-0.22309},\"start_location\":{\"latitude\":51.74802340000001,\"longitude\":-0.2320111},\"number_passengers\":1}")
                .and()
                .response()
                .body("status", equalTo("0"))
                .when()
                .post("http://127.0.0.1:8080/api/v1/booking");
    }
    
    
    /**
     * Test method makeBooking of BookingController. 
     * Test: same start and destination location.
     */
    @Test
    public void testMakeBookingSameStartDestination() {
        expect()
                .statusCode(400)
                .request()
                .header("Authorization", "Basic am9obi5kb2U6cGFzc3dvcmQ=")
                .header("Content-Type", "application/json")
                .body("{\"end_location\":{\"latitude\":51.763366,\"longitude\":-0.22309},\"start_location\":{{\"latitude\":51.763366,\"longitude\":-0.22309},\"number_passengers\":1}")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://127.0.0.1:8080/api/v1/booking");
    }
}