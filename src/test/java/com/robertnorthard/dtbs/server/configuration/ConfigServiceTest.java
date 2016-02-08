package com.robertnorthard.dtbs.server.configuration;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ConfigService
 * 
 * @author robertnorthard
 */
public class ConfigServiceTest {
    
    /**
     * Test of parseProperty method with valid tokens.
     */
    @Test
    public void testParseProperty() {
        
        // property to tokenise.
        String property = "https://maps.googleapis.com/maps/api/directions/json?origin={origin}&destination={destination}";

        // tokens and respective values.
        Map<String,String> tokens = new HashMap<>();
        tokens.put("origin", "Hatfield");
        tokens.put("destination","Welwyn");

        // expected tokinzed url
        String expectedTokenisedProperty = "https://maps.googleapis.com/maps/api/directions/json?origin=Hatfield&destination=Welwyn";
        
        assertTrue(ConfigService.parseProperty(property, tokens).equals(expectedTokenisedProperty));
    }
    
   /**
     * Test of parseProperty method with invalid tokens.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testParsePropertyNullArguments(){
        ConfigService.parseProperty(null, null);
    }
}
