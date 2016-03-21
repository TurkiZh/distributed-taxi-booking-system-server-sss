package com.robertnorthard.dtbs.server;

import com.robertnorthard.dtbs.server.layer.controllers.rest.AccountController;
import com.robertnorthard.dtbs.server.layer.controllers.rest.AuthenticationController;
import com.robertnorthard.dtbs.server.layer.controllers.rest.BookingController;
import com.robertnorthard.dtbs.server.layer.controllers.rest.GeocodeController;
import com.robertnorthard.dtbs.server.layer.controllers.rest.TaxiController;
import com.robertnorthard.dtbs.server.layer.controllers.rest.TestController;
import com.robertnorthard.dtbs.server.layer.security.AuthenticationFilter;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Application configuration.
 *
 * @author robertnorthard
 *
 */
@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {

        // RESTful Controllers
        register(AccountController.class);
        register(AuthenticationController.class);
        register(GeocodeController.class);
        register(BookingController.class);
        register(TaxiController.class);
        register(TestController.class);

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
