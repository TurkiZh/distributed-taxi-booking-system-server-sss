package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponseFactory;
import com.robertnorthard.dtbs.server.layer.service.GoogleDistanceMatrixFacade;
import com.robertnorthard.dtbs.server.layer.utils.geocoding.MapUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author robertnorthard
 */
@Path("/distance")
@RequestScoped
public class DistanceMatrixController {

    private static final Logger LOGGER = Logger.getLogger(DistanceMatrixController.class.getName());

    private final HttpResponseFactory responseFactory;
    @Inject private GoogleDistanceMatrixFacade googleDistanceMatrixService;

    public DistanceMatrixController() {
        this.responseFactory = HttpResponseFactory.getInstance();
    }

    @GET
    @Path("/geocode")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response addressReverseLookup(@Context UriInfo url) {

        MultivaluedMap<String, String> query = url.getQueryParameters();

        try {

            if (!(query.containsKey("latitude") && query.containsKey("longitude"))) {
                throw new IllegalArgumentException("Latitude and longitude parameter must be provided.");
            }

            double latitude = Double.parseDouble(query.getFirst("latitude"));
            double longitude = Double.parseDouble(query.getFirst("longitude"));

            if (!MapUtils.validateCoordinates(latitude, longitude)) {
                throw new IllegalArgumentException("Invalid latitude and longitude parameters.");
            }

            LOGGER.log(Level.INFO, "addressReverseLookup - LatLng {0},{1}", 
                    new Object[]{latitude, longitude});

            String address = this.googleDistanceMatrixService.getGeocode(latitude, longitude);
            
            LOGGER.log(Level.INFO, "addressReverseLookup - {0}", address);

            // Zero resuls found for address reverse lookup using latitude and longitude
            if (address == null) {

                return this.responseFactory.getResponse(
                        "Address not found.", Response.Status.NOT_FOUND);
            }

            return this.responseFactory.getResponse(address, Response.Status.OK);

        } catch (IllegalArgumentException | JSONException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
}
