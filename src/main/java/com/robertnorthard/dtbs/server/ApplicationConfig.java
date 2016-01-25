package com.robertnorthard.dtbs.server;

import com.robertnorthard.dtbs.server.layer.controllers.AccountController;
import com.robertnorthard.dtbs.server.layer.controllers.LoginController;
import com.robertnorthard.dtbs.server.layer.security.AuthenticationFilter;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Application configuration.
 * @author robertnorthard
 **/
@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig(){
        
        // RESTful Controllers
        register(AccountController.class);
        register(LoginController.class);
        
        // Filters        
        /* 
        In order to use security annotations (package javax.annotation.security) 
        to restrict access to your resources you need to register RolesAllowedDynamicFeature.
        Source: http://stackoverflow.com/questions/21536321/securitycontext-doesnt-work-with-rolesallowed
        */
        register(RolesAllowedDynamicFeature.class);
        register(AuthenticationFilter.class);
    }
}
 