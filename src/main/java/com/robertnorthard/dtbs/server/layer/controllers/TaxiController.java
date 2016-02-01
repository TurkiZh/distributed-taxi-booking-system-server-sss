package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponseFactory;
import com.robertnorthard.dtbs.server.layer.service.LocationTrackingService;
import com.robertnorthard.dtbs.server.layer.service.TaxiFacade;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Controller class for taxis.
 * @author robertnorthard
 */
@Path("/taxi")
@RequestScoped
public class TaxiController {
    
    private static final Logger LOGGER = Logger.getLogger(TaxiController.class.getName());
    
    private final HttpResponseFactory responseFactory;
    private final LocationTrackingService locationTrackingService;
    @Inject private TaxiFacade taxiService;
    private final DataMapper mapper;
    
    public TaxiController(){
        this.responseFactory = HttpResponseFactory.getInstance();
        this.locationTrackingService = LocationTrackingService.getInstance();
        this.mapper = DataMapper.getInstance();
    }
    
    @POST
    @Path("/{id}/location")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response updateTaxiLocation(@PathParam("id") Long id, String message) {
         try { 
             
            Location location = this.mapper.readValue(message, Location.class);
            
            this.locationTrackingService.updateLocation(
                    id, location.getLatitude(), location.getLongitude(), new Date().getTime());
            
            LOGGER.log(Level.INFO, "updateTaxiLocation - Lat: {0} Lng:{1}", 
                    new Object[]{location.getLatitude(), location.getLongitude()});
            
            return this.responseFactory.getResponse(
                    "Taxi location updated.", Response.Status.OK);
            
        } catch (EntityNotFoundException ex) {
            
            LOGGER.log(Level.INFO, null, ex);
            
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.NOT_FOUND);
            
        } catch (IOException|IllegalArgumentException ex) { 
            
            LOGGER.log(Level.WARNING, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("/{id}") 
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getTaxi(@PathParam("id") Long id){
        
        Taxi taxi = this.taxiService.findTaxi(id);
        
        if(taxi!=null){
            return this.responseFactory.getResponse(
                    taxi, Response.Status.OK);
        }else{
            LOGGER.log(Level.INFO, "getTaxi - Taxi not found");
            return this.responseFactory.getResponse(
                    "Taxi not found.", Response.Status.NOT_FOUND);
        }
    }
}
