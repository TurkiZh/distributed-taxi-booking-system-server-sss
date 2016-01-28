package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponse;
import com.robertnorthard.dtbs.server.layer.services.AccountService;
import com.robertnorthard.dtbs.server.layer.services.AccountServiceImpl;
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

/**
 * Account controller for managing external interactions with data model.
 * @author robertnorthard
 */
@Path("/auth")
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    private final AccountService accountService;
    private final DataMapper mapper;

    public LoginController() {
        this.accountService = new AccountServiceImpl();
        this.mapper = DataMapper.getInstance();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public String login(String credentials) {
        try {
            Account ac = this.mapper.readValue(credentials, Account.class);
            ac = this.accountService
                    .authenticate(ac.getUsername(), ac.getPassword());

            return new HttpResponse(ac,"0").toString();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return new HttpResponse(
                    ex.getMessage(),
                    "1"
            ).toString();
        } catch (AccountAuthenticationFailed ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return new HttpResponse(
                    ex.getMessage(),
                    "2"
            ).toString();
        }
    }
}
