package com.robertnorthard.dtbs.server.layer.services;

import com.robertnorthard.dtbs.server.layer.utils.AuthenticationUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Authentication Utilities unit tests.
 * @author robertnorthard
 */
public class AuthenticationServiceImplTest {

    @Test
    public void checkPassword() {
        assertTrue(AuthenticationUtils
                .checkPassword("robertnorthard", 
                        "$2a$12$PCE2KE0b26mrJSfbxU8ep.XGxVisAc9BqJbxR6FD9BFLLAYGNipg."));
    }
    
    @Test
    public void checkHash() {
        String hashedPassword = AuthenticationUtils.hashPassword("Password01");
        assertTrue(AuthenticationUtils
                .checkPassword("Password01",
                       AuthenticationUtils
                                .hashPassword("Password01")));
    }
    
    @Test
    public void generateCode(){
        assertTrue(AuthenticationUtils.generateCode(10).length() == 10);
        assertTrue(AuthenticationUtils.generateCode("1", 4).equals("1111"));
        assertTrue(AuthenticationUtils.generateCode(4).length() == 4);
    }
}
