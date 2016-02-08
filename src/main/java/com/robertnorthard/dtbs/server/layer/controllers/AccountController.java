package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.common.exceptions.AccountAlreadyExistsException;
import com.robertnorthard.dtbs.server.common.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.common.exceptions.AccountInvalidException;
import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponseFactory;
import com.robertnorthard.dtbs.server.layer.service.AccountFacade;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import com.robertnorthard.dtbs.server.layer.model.Account;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * A controller class for receiving and handling all account event related
 * transactions.
 *
 * @author robertnorthard
 */
@Path("/v1/account")
@RequestScoped
public class AccountController {

    private static final Logger LOGGER = Logger.getLogger(AccountController.class.getName());

    @Inject
    private AccountFacade accountService;
    private final DataMapper mapper;
    private final HttpResponseFactory responseFactory;

    public AccountController() {
        this.mapper = DataMapper.getInstance();
        this.responseFactory = HttpResponseFactory.getInstance();
    }

    /**
     * Register new account.
     *
     * @param account JSON representation of an account.
     * @return account object if successful else an appropriate error message: -
     * IOException - invalid JSON - AccountAlreadyExistsException - account with
     * username already exists. - AccountInvalidException - invalid account
     * details.
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response registerAccount(String account) {
        try {
            Account ac = this.mapper.readValue(account, Account.class);

            this.accountService.registerAccount(ac);

            return this.responseFactory.getResponse(
                    ac, Response.Status.OK);

        } catch (AccountAlreadyExistsException ex) {

            LOGGER.log(Level.WARNING, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.CONFLICT);

        } catch (AccountInvalidException | IOException ex) {

            LOGGER.log(Level.WARNING, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Reset account password.
     *
     * @param username inferred from path parameter.
     * @return account reset confirmation.
     */
    @POST
    @Path("/{username}/reset")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response resetAccount(@PathParam("username") String username) {

        try {
            this.accountService.resetPassword(username);

            LOGGER.log(Level.INFO, "resettAccount - resetting password " + username);

            return this.responseFactory.getResponse(
                    "Password reset sent", Response.Status.OK);

        } catch (AccountInvalidException ex) {

            LOGGER.log(Level.WARNING, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }

    /**
     * Reset an account's password using temporary access code.
     *
     * @param username username of account to reset.
     * @param code temporary code.
     * @param message JSON message with new password.
     * @return AccountAuthenticationException if code in invalid and
     * EntityNotFoundException if unable to find account.
     */
    @POST
    @Path("/{username}/reset/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response resetPassword(@PathParam("username") String username, @PathParam("code") String code, String message) {

        try {
            JSONObject object = new JSONObject(message);

            this.accountService.resetPassword(code, username, object.getString("password"));

            return this.responseFactory.getResponse(
                    "Password change successful", Response.Status.OK);

        } catch (JSONException ex) {

            LOGGER.log(Level.WARNING, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);

        } catch (AccountAuthenticationFailed ex) {

            LOGGER.log(Level.WARNING, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.UNAUTHORIZED);

        } catch (EntityNotFoundException ex) {

            LOGGER.log(Level.WARNING, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }
}
