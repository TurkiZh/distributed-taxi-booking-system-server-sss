package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.Booking;
import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.model.Route;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import com.robertnorthard.dtbs.server.layer.model.Vehicle;
import com.robertnorthard.dtbs.server.layer.model.VehicleType;
import com.robertnorthard.dtbs.server.layer.persistence.AccountDao;
import com.robertnorthard.dtbs.server.layer.persistence.TaxiDao;
import com.robertnorthard.dtbs.server.layer.persistence.VehicleDao;
import com.robertnorthard.dtbs.server.layer.persistence.VehicleTypeDao;
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
        
        
        // TODO
        
        Principal username = securityContext.getUserPrincipal();
        
        
        return null;
        
    }
    
    @GET
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public String bookTaxi() throws JSONException{
        

        
        VehicleType vt = new VehicleTypeDao().findEntityById(1l);
        
        Vehicle vehicle = new Vehicle("numberplate",5, vt);
        
        //new VehicleDao().persistEntity(vehicle);
        
        Account account = new AccountDao().findEntityById("nln");
        
        
        vehicle = new VehicleDao().findEntityById("numberplate");
        
         
         
        Taxi taxi = new Taxi(vehicle,account);
        
        new TaxiDao().persistEntity(taxi);
        
        //Location l1 = new Location(123123123, 12312124);
        //Location l2 = new Location(7876, 678678678);
        //List<Location> li = new ArrayList<>();
        
        //Route route = new Route(l1, l2, 10, new GoogleDistanceMatrixServiceImpl().getRoutes(HttpUtils.getUrl("https://maps.googleapis.com/maps/api/directions/json?origin=hatfield&destination=welywn&key=AIzaSyDY6IVa8pcYJD4lMDbFaCayQr6327cyqMc")).get(0), new Date());
        
       // Booking b = new Booking(taxi, route, 5);
         
        return new HttpResponse(new TaxiDao().findEntityById(601L),"0").toString();
    }
}
 