package com.robertnorthard.dtbs.server.layer.utils.http;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * HTTP utility class unit tests.
 * @author robertnorthard
 */
public class HttpUtilsTest {
    
    /**
     * Test of stringEncode method, of class HttpUtils.
     */
    @Test
    public void testStringEncode() {
        String value = "hatfield to welywn";
        String expResult = "hatfield%20to%20welywn";
        String result = HttpUtils.stringEncode(value);
        
        assertEquals(expResult, result);
        assertEquals(HttpUtils.stringEncode(expResult),expResult);
    }
}
