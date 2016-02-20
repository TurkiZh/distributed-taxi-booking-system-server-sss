package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.common.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.common.exceptions.BookingNotFoundException;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidLocationException;
import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidBookingException;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidGoogleApiResponseException;
import com.robertnorthard.dtbs.server.common.exceptions.RouteNotFoundException;
import com.robertnorthard.dtbs.server.common.exceptions.TaxiNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.booking.Booking;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpListResponse;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponseFactory;
import com.robertnorthard.dtbs.server.layer.service.BookingFacade;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * A controller class for receiving and handling all booking related
 * transactions.
 *
 * @author robertnorthard
 */
@Path("/v1/booking")
@RequestScoped
public class BookingController {

    private static final Logger LOGGER = Logger.getLogger(BookingController.class.getName());

    @Inject
    private BookingFacade bookingService;
    private final HttpResponseFactory responseFactory;
    private final DataMapper mapper;

    public BookingController() {
        this.responseFactory = HttpResponseFactory.getInstance();
        this.mapper = DataMapper.getInstance();
    }

    /**
     * Create a new taxi booking.
     *
     * @param securityContext injected by request scope
     * @param message JSON representation of a booking data access object.
     * @return booking object with estimated cost, distance, and travel time. A
     * driver will not have been notified at this time.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("passenger")
    public Response makeBooking(@Context SecurityContext securityContext, String message) {

        BookingDto bookingDto = null;
        Booking booking = null;

        try {
            if (securityContext != null) {
                String username = securityContext.getUserPrincipal().getName();

                bookingDto = this.mapper.readValue(message, BookingDto.class);

                if (username != null) {
                    /*
                     Security flow - don't allow people to book taxis for other user's. 
                     Set username from security context.
                     */
                    bookingDto.setPassengerUsername(username);
                    booking = this.bookingService.makeBooking(bookingDto);
                }
            } else {
                throw new AccountAuthenticationFailed();
            }

            return this.responseFactory.getResponse(booking, Response.Status.OK);

        } catch (AccountAuthenticationFailed ex) {

            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.UNAUTHORIZED);
        } catch (InvalidLocationException | InvalidBookingException | RouteNotFoundException ex) {

            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.NOT_FOUND);

        } catch (IOException | IllegalArgumentException ex) {

            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);

        } catch (InvalidGoogleApiResponseException ex) {

            LOGGER.log(Level.SEVERE, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Return booking history based on user roles.
     *
     * @param securityContext
     * @return booking history based on user roles.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"passenger", "driver"})
    public Response bookingHistory(@Context SecurityContext securityContext) {

        if (securityContext != null) {

            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new HttpListResponse<>(
                                    this.bookingService.findBookingHistory(
                                            securityContext.getUserPrincipal().getName()), "0").toString()).build();
        }

        LOGGER.log(Level.INFO, "bookingHistory - user not authenticated");
        return this.responseFactory.getResponse(
                "User not authenticated", Response.Status.UNAUTHORIZED);
    }

    /**
     * Return a collection of bookings in awaiting taxi dispatch state.
     *
     * @return a collection of bookings in awaiting taxi dispatch state.
     */
    @GET
    @Path("/awaiting")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("driver")
    public Response findBookingsAwaitingTaxiDispatch() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new HttpListResponse<>(
                                this.bookingService.findBookingsInAwaitingTaxiDispatchState(), "0").toString()).build();
    }

    /**
     * Find a booking.
     *
     * @param securityContext user's security context injected by container.
     * @param id id of booking.
     * @return the booking object. If the user is authenticated to view the
     * booking.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("passenger")
    public Response findBooking(@Context SecurityContext securityContext, @PathParam("id") long id) {

        try {
            if (securityContext != null) {

                Booking booking = this.bookingService.findBookingForUser(
                        id,
                        securityContext.getUserPrincipal().getName());

                if (booking == null) {
                    throw new EntityNotFoundException();
                }

                return this.responseFactory.getResponse(booking, Response.Status.OK);

            } else {
                throw new AccountAuthenticationFailed();
            }

        } catch (AccountAuthenticationFailed ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.UNAUTHORIZED);
        } catch (EntityNotFoundException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }

    /**
     * Accept a taxi booking,
     *
     * @param securityContext user's security context injected by container.
     * @param bookingId of booking to accept.
     * @return the booking object. If the user is authenticated to view the
     * booking.
     */
    @POST
    @Path("/{id}/accept")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("driver")
    public Response acceptBooking(@Context SecurityContext securityContext, @PathParam("id") long bookingId) {

        try {
            if (securityContext != null) {

                this.bookingService.acceptBooking(
                        securityContext.getUserPrincipal().getName(), bookingId);

                return this.responseFactory.getResponse("Booking updated.", Response.Status.OK);

            } else {
                throw new AccountAuthenticationFailed();
            }

        } catch (AccountAuthenticationFailed ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.UNAUTHORIZED);
        } catch (TaxiNotFoundException | BookingNotFoundException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }
    
    /**
     * Cancel a taxi booking,
     *
     * @param securityContext user's security context injected by container.
     * @param bookingId of booking to accept.
     * @return the booking object. If the user is authenticated to view the
     * booking.
     */
    @POST
    @Path("/{id}/cancel")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("passenger")
    public Response cancelBooking(@Context SecurityContext securityContext, @PathParam("id") long bookingId) {

        try {
            if (securityContext != null) {

                this.bookingService.cancelBooking(
                        securityContext.getUserPrincipal().getName(), bookingId);

                return this.responseFactory.getResponse("Booking Cancelled.", Response.Status.OK);

            } else {
                throw new AccountAuthenticationFailed();
            }

        } catch (AccountAuthenticationFailed ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.UNAUTHORIZED);
        } catch (BookingNotFoundException ex) {
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.NOT_FOUND);
        } catch(IllegalStateException ex){
            LOGGER.log(Level.INFO, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
}
