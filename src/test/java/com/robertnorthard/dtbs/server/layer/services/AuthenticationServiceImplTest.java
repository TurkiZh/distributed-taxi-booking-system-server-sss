package com.robertnorthard.dtbs.server.layer.services;

import com.robertnorthard.dtbs.server.layer.services.AuthenticationService;
import com.robertnorthard.dtbs.server.layer.services.AuthenticationServiceImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Authentication Service unit tests.
 * @author robertnorthard
 */
public class AuthenticationServiceImplTest {
    
    private final AuthenticationService authenticationService;
    
    public AuthenticationServiceImplTest() {
        this.authenticationService = new AuthenticationServiceImpl();
    }

    @Test
    public void checkPassword() {
        assertTrue(this.authenticationService
                .checkPassword("robertnorthard", 
                        "$2a$12$PCE2KE0b26mrJSfbxU8ep.XGxVisAc9BqJbxR6FD9BFLLAYGNipg."));
    }
    
    @Test
    public void checkHash() {
        String hashedPassword = this.authenticationService.hashPassword("Password01");
        assertTrue(this.authenticationService
                .checkPassword("Password01",
                        this.authenticationService
                                .hashPassword("Password01")));
    }
    
    @Test
    public void generatePassword(){
        assertTrue(this.authenticationService.generateCode(10).length() == 10);
        assertTrue(this.authenticationService.generateCode("1", 4).equals("1111"));
    }
}
