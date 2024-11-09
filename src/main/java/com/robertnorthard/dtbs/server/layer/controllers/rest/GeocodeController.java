package com.robertnorthard.dtbs.server.layer.controllers.rest;

import com.robertnorthard.dtbs.server.common.exceptions.InvalidGoogleApiResponseException;
import com.robertnorthard.dtbs.server.layer.service.entities.Location;
import com.robertnorthard.dtbs.server.layer.service.entities.Route;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponseFactory;
import com.robertnorthard.dtbs.server.layer.service.GoogleDistanceMatrixFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
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

/**
 * A controller class for receiving and handling all geocoding related transactions.
 *
 * @author robertnorthard
 */
@Path("/v1/geocode")
@RequestScoped
public class GeocodeController {

    private static final Logger LOGGER = Logger.getLogger(GeocodeController.class.getName());

    private final HttpResponseFactory responseFactory;
    @Inject
    private GoogleDistanceMatrixFacade googleDistanceMatrixService;

    public GeocodeController() {
        this.responseFactory = HttpResponseFactory.getInstance();
    }

    public double start_latitude;
    public double start_longitude;
    public double end_latitude;
    public double end_longitude;

    /**
     * Return a route from start and end location latitude and longitude.
     *
     * @param url url with start_latitude, start_longitude, end_latitude and end_longitude query parameters.
     * @return a route from start to end location using provided a latitudes and longitudes.
     */
    @GET
    @Path("/route")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"driver", "passenger"})
    public Response getRouteInfo(@Context UriInfo url) {

        MultivaluedMap<String, String> query = url.getQueryParameters();

        try {
            if (!(query.containsKey("start_latitude") && query.containsKey("end_latitude")
                    && query.containsKey("end_longitude") && query.containsKey("start_longitude"))) {
                throw new IllegalArgumentException("Latitude and longitude parameter must be provided for start and end locations.");
            }

            start_latitude = Double.parseDouble(query.getFirst("start_latitude"));
            start_longitude = Double.parseDouble(query.getFirst("start_longitude"));
            end_latitude = Double.parseDouble(query.getFirst("end_latitude"));
            end_longitude = Double.parseDouble(query.getFirst("end_longitude"));

            Route route = this.googleDistanceMatrixService.getRouteInfo(new Location(start_latitude, start_longitude), new Location(end_latitude, end_longitude));

            return this.responseFactory.getResponse(route, Response.Status.OK);

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Return address corresponding to provided latitude and longitude.
     *
     * @param url url with latitude and longitude query parameters.
     * @return address corresponding to provided latitude and longitude.
     */
    @GET
    @Path("/reverse")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"driver", "passenger"})
    public Response addressReverseLookup(@Context UriInfo url) {

        MultivaluedMap<String, String> query = url.getQueryParameters();

        try {

            if (!(query.containsKey("latitude") && query.containsKey("longitude"))) {
                throw new IllegalArgumentException("Latitude and longitude parameter must be provided.");
            }

            double latitude = Double.parseDouble(query.getFirst("latitude"));
            double longitude = Double.parseDouble(query.getFirst("longitude"));

            if (!Location.validateCoordinates(latitude, longitude)) {
                throw new IllegalArgumentException("Latitude or longitude are invalid.");
            }

            LOGGER.log(Level.INFO, "addressReverseLookup - LatLng {0},{1}",
                    new Object[]{latitude, longitude});

            String address = this.googleDistanceMatrixService.getGeocode(
                    latitude, longitude);

            LOGGER.log(Level.INFO, "addressReverseLookup - {0}", address);

            // Zero resuls found for address reverse lookup using latitude and longitude
            if (address == null) {
                return this.responseFactory.getResponse(
                        "Address not found.", Response.Status.NOT_FOUND);
            }

            return this.responseFactory.getResponse(address, Response.Status.OK);

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Return location (lat/lng) corresponding to provided address.
     *
     * @param url url with address query parameter.
     * @return address corresponding to provided latitude and longitude.
     */
    @GET
    @Path("/address/lookup")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"driver", "passenger"})
    public Response addressLookup(@Context UriInfo url) {

        MultivaluedMap<String, String> query = url.getQueryParameters();

        try {

            if (!(query.containsKey("address"))) {
                throw new IllegalArgumentException("Address must be provided");
            }

            String address = query.getFirst("address");

            LOGGER.log(Level.INFO, "addressLookup - {0}", address);

            Location location = this.googleDistanceMatrixService.getGeocode(
                    address);

            if (location == null) {
                return this.responseFactory.getResponse(
                        "Address not found.", Response.Status.NOT_FOUND);
            }

            LOGGER.log(Level.INFO, "addressLookup - LatLng {0},{1}",
                    new Object[]{location.getLatitude(), location.getLongitude()});

            return this.responseFactory.getResponse(location, Response.Status.OK);

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Return estimate travel time between start and end location in seconds.
     *
     * @param url url with start_latitude, start_longitude, end_latitude and end_longitude query parameters.
     * @return a route from start to end location using provided a latitudes and longitudes.
     */
    @GET
    @Path("/route/estimate/time")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"driver", "passenger"})
    public Response estimateTravelTime(@Context UriInfo url) {

        MultivaluedMap<String, String> query = url.getQueryParameters();

        try {
            if (!(query.containsKey("start_latitude") && query.containsKey("end_latitude")
                    && query.containsKey("end_longitude") && query.containsKey("start_longitude"))) {
                throw new IllegalArgumentException("Latitude and longitude parameter must be provided for start and end locations.");
            }

            start_latitude = Double.parseDouble(query.getFirst("start_latitude"));
            start_longitude = Double.parseDouble(query.getFirst("start_longitude"));
            end_latitude = Double.parseDouble(query.getFirst("end_latitude"));
            end_longitude = Double.parseDouble(query.getFirst("end_longitude"));

            double time = this.googleDistanceMatrixService.estimateTravelTime(new Location(start_latitude, start_longitude), new Location(end_latitude, end_longitude));

            return this.responseFactory.getResponse(time, Response.Status.OK);

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Find address via textual description.
     *
     * @param url url with address query parameter.
     * @return the found address, else "Address not found.".
     */
    @GET
    @Path("/address")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"driver", "passenger"})
    public Response findAddress(@Context UriInfo url) {

        MultivaluedMap<String, String> query = url.getQueryParameters();

        try {
            if (!(query.containsKey("address"))) {
                throw new IllegalArgumentException("Address must be provided.");
            }

            String address = this.googleDistanceMatrixService.findAddress(query.getFirst("address"));

            if (address == null) {
                return this.responseFactory.getResponse(
                        "Address not found.", Response.Status.NOT_FOUND);
            }

            return this.responseFactory.getResponse(address, Response.Status.OK);

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);

        } catch (InvalidGoogleApiResponseException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
