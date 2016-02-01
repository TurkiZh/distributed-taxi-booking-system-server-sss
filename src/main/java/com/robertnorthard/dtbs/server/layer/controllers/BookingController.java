package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.layer.persistence.TaxiDao;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponse;
import java.security.Principal;
import javax.annotation.security.PermitAll;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.codehaus.jettison.json.JSONException;

/**
 * A controller class for receiving and handling all taxi event related transactions. 
 * @author robertnorthard
 */
@Path("/booking")
public class BookingController {
    
    
    /**
     * Return the users bookings.
     * @param securityContext
     * @return 
     * @throws JSONException 
     */
    @GET
    @Path("/b")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response viewBookingHistory(@Context SecurityContext securityContext) throws JSONException{
        
        return null;
        
    }
    
    @GET
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public String bookTaxi() throws JSONException{
        

     
         
        return new HttpResponse(new TaxiDao().findEntityById(601L),"0").toString();
    }
}
 