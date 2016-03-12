package com.robertnorthard.dtbs.server.layer.controllers;

import static com.jayway.restassured.RestAssured.expect;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;

/**
 *
 * @author robertnorthard
 */
public class AccountControllerIT {

    /**
     * Test method registerAccount of AccountController. 
     * Test: missing family and common name.
     */
    @Test
    public void testRegisterAccount1() {
        expect()
                .statusCode(400)
                .request()
                .header("Content-Type", "application/json")
                .body("{\n"
                        + "  \"username\": \"robert\",\n"
                        + "  \"common_name\":\"\",\n"
                        + "  \"family_name\":\"\",\n"
                        + "  \"password\": \"password01\",\n"
                        + "  \"email\": \"john.doe@example.com\",\n"
                        + "  \"phone_number\":\"07526080889\",\n"
                        + "  \"role\":\"PASSENGER\"\n"
                        + "}")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://localhost:8080/api/v1/account/register");
    }
    
    /**
     * Test method registerAccount of AccountController. 
     * Test: invalid username
     */
    @Test
    public void testRegisterAccount2() {
        expect()
                .statusCode(400)
                .request()
                .header("Content-Type", "application/json")
                .body("{\n"
                        + "  \"username\": \"\",\n"
                        + "  \"common_name\":\"name\",\n"
                        + "  \"family_name\":\"name\",\n"
                        + "  \"password\": \"password01\",\n"
                        + "  \"email\": \"john.doe@example.com\",\n"
                        + "  \"phone_number\":\"07526080889\",\n"
                        + "  \"role\":\"PASSENGER\"\n"
                        + "}")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://localhost:8080/api/v1/account/register");
    }
    
    /**
     * Test method registerAccount of AccountController. 
     * Test: invalid phone number.
     */
    @Test
    public void testRegisterAccount3() {
        expect()
                .statusCode(400)
                .request()
                .header("Content-Type", "application/json")
                .body("{\n"
                        + "  \"username\": \"robert\",\n"
                        + "  \"common_name\":\"name\",\n"
                        + "  \"family_name\":\"name\",\n"
                        + "  \"password\": \"password01\",\n"
                        + "  \"email\": \"john.doe@example.com\",\n"
                        + "  \"phone_number\":\"1111111111\",\n"
                        + "  \"role\":\"PASSENGER\"\n"
                        + "}")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://localhost:8080/api/v1/account/register");
    }

    /**
     * Test method registerAccount of AccountController. 
     * Test: invalid email.
     */
    @Test
    public void testRegisterAccount4() {
        expect()
                .statusCode(400)
                .request()
                .header("Content-Type", "application/json")
                .body("{\n"
                        + "  \"username\": \"robert\",\n"
                        + "  \"common_name\":\"name\",\n"
                        + "  \"family_name\":\"name\",\n"
                        + "  \"password\": \"password01\",\n"
                        + "  \"email\": \"john.doeexample.com\",\n"
                        + "  \"phone_number\":\"11111111111\",\n"
                        + "  \"role\":\"PASSENGER\"\n"
                        + "}")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://localhost:8080/api/v1/account/register");
    }

    /**
     * Test method registerAccount of AccountController. 
     * Test: all fields blank.
     */
    @Test
    public void testRegisterAccount5() {
        expect()
                .statusCode(400)
                .request()
                .header("Content-Type", "application/json")
                .body("{\n"
                        + "  \"username\": \"\",\n"
                        + "  \"common_name\":\"\",\n"
                        + "  \"family_name\":\"\",\n"
                        + "  \"password\": \"\",\n"
                        + "  \"email\": \"\",\n"
                        + "  \"phone_number\":\"\",\n"
                        + "  \"role\":\"\"\n"
                        + "}")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://localhost:8080/api/v1/account/register");
    }

   /**
     * Test method registerAccount of AccountController. 
     * Test: username less than 6 characters.
     */
    @Test
    public void testRegisterAccount6() {
        expect()
                .statusCode(400)
                .request()
                .header("Content-Type", "application/json")
                .body("{\n"
                        + "  \"username\": \"rober\",\n"
                        + "  \"common_name\":\"name\",\n"
                        + "  \"family_name\":\"name\",\n"
                        + "  \"password\": \"password01\",\n"
                        + "  \"email\": \"john.doe@example.com\",\n"
                        + "  \"phone_number\":\"11111111111\",\n"
                        + "  \"role\":\"PASSENGER\"\n"
                        + "}")
                .and()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://localhost:8080/api/v1/account/register");
    }

    
    /**
     * Test method resetAccount of AccountController. 
     * Test: Account does not exist.
     */
    @Test
    public void testResetAccount7() {
        expect()
                .statusCode(404)
                .request()
                .response()
                .body("status", equalTo("4"))
                .when()
                .post("http://localhost:8080/api/v1/account/invalidAccountName/reset");
    }

    /**
     * Test method resetPassword of AccountController. 
     * Test: Account does not exist
     */
    @Test
    public void testResetPassword1() {
        expect()
                .statusCode(401)
                .request()
                .header("Content-Type", "application/json")
                .body("{\n"
                        + "  \"password\": \"robert\"" +
                        "}")
                .and()
                .response()
                .body("status", equalTo("1"))
                .when()
                .post("http://localhost:8080/api/v1/account/invalidAccountName/reset/100");
    }
    
    /**
     * Test method resetPassword of AccountController. 
     * Test: New password not provided.
     */
    @Test
    public void testResetPassword2() {
        expect()
                .statusCode(400)
                .request()
                .response()
                .body("status", equalTo("3"))
                .when()
                .post("http://localhost:8080/api/v1/account/invalidAccountName/reset/100");
    }
}
