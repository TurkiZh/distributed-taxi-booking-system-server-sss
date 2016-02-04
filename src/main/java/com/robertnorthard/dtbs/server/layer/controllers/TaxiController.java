package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponseFactory;
import com.robertnorthard.dtbs.server.layer.service.LocationTrackingService;
import com.robertnorthard.dtbs.server.layer.service.TaxiFacade;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import com.robertnorthard.dtbs.server.layer.model.Location;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
@Path("/v1/taxi")
@RequestScoped
public class TaxiController {
    
    private static final Logger LOGGER = Logger.getLogger(TaxiController.class.getName());
    
    @Inject private LocationTrackingService locationTrackingService;
    @Inject private TaxiFacade taxiService;
    private final HttpResponseFactory responseFactory;
    private final DataMapper mapper;
    
    /**
     * Default constructor for class taxi controller.
     */
    public TaxiController(){
        this.responseFactory = HttpResponseFactory.getInstance();
        this.mapper = DataMapper.getInstance();
    }
    
    /**
     * Update location of taxi associated with the provided id.
     * 
     * @param id id of the taxi.
     * @param message the json update message.
     * @return update confirmation.
     */
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
            
            LOGGER.log(Level.INFO, "updateTaxiLocation - Taxi Id: {0} Lat: {1} Lng:{2}", 
                    new Object[]{ id, location.getLatitude(), location.getLongitude()});
            
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
    
    /**
     * Find taxi by id.
     * 
     * @param id taxi id.
     * @return taxi with specified id else 404. 
     */
    @GET
    @Path("/{id}") 
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"driver","passenger"})
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
    
    /**
     * Set passenger to picked up.
     */
    
    
    /**
     * Send passenger to dropped of.
     */
    
    
    /**
     * Drive accepts job.
     */
}
