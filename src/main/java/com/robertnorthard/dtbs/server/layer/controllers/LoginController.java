package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponseFactory;
import com.robertnorthard.dtbs.server.layer.service.AccountFacade;
import com.robertnorthard.dtbs.server.layer.service.AccountServiceImpl;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A controller class for receiving and handling all authentication related transactions. 
 * @author robertnorthard
 */
@Path("/auth")
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    private final AccountFacade accountService;
    private final DataMapper mapper;
    private final HttpResponseFactory responseFactory;

    public LoginController() {
        this.accountService = new AccountServiceImpl();
        this.mapper = DataMapper.getInstance();
        this.responseFactory = HttpResponseFactory.getInstance();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(String credentials) {
        try { 
            Account ac = this.mapper.readValue(credentials, Account.class);
            ac = this.accountService
                    .authenticate(ac.getUsername(), ac.getPassword());

            return this.responseFactory.getResponse(
                    ac, Response.Status.OK);
            
        } catch (IOException ex) {
            
            LOGGER.log(Level.SEVERE, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.BAD_REQUEST);

        } catch (AccountAuthenticationFailed ex) {
            
            LOGGER.log(Level.SEVERE, null, ex);
            return this.responseFactory.getResponse(
                    ex.getMessage(), Response.Status.UNAUTHORIZED);
        }
    }
}
