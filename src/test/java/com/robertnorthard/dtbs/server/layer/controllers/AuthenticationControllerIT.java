package com.robertnorthard.dtbs.server.layer.controllers;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;

/**
 * Integration test for Authentication Controller.
 * 
 * @author robertnorthard
 */
public class AuthenticationControllerIT {
    
    public AuthenticationControllerIT() {
    }

    /**
     * Test method login of AuthenticatioController.
     * Test: invalid account.
     */
    @Test
    public void testLoginInvalidAccount() {
        expect()
                .statusCode(401)
                .request()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"not_a_user\", \"password\":\"password\",\"gcm_reg_id\":\"asdasdasd\"}")
                .and()
                .response()
                .body("status", equalTo("1"))
                .when()
                .post("http://localhost:8080/api/v1/auth/login");
    }

       
    /** 
     * Test method login of AuthenticatioController.
     * Test: valid account.
     */
    @Test
    public void testLoginValidAccount() {
        expect()
                .statusCode(200)
                .request()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"john.doe\", \"password\":\"password\",\"gcm_reg_id\":\"asdasdasd\"}")
                .and()
                .response()
                .body("status", equalTo("0"))
                .when()
                .post("http://localhost:8080/api/v1/auth/login");
    }
    
    /**
     * Test method login of AuthenticatioController.
     * Test: malformed JSON.
     */
    @Test
    public void testLoginMalformedJson() {
        expect()
                .statusCode(500)
                .request()
                .header("Content-Type", "application/json")
                .body("{}")
                .when()
                .post("http://localhost:8080/api/v1/auth/login");
    }
}
