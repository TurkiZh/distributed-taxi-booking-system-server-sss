package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.exceptions.AccountAlreadyExistsException;
import com.robertnorthard.dtbs.server.exceptions.AccountInvalidException;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpErrorResponse;
import com.robertnorthard.dtbs.server.layer.services.AccountService;
import com.robertnorthard.dtbs.server.layer.services.AccountServiceImpl;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Account controller for managing external interactions with data model.
 * @author robertnorthard
 */
@Path("/account")
public class AccountController {
    
    private final AccountService accountService;
    private final DataMapper mapper;
    
    public AccountController(){
        this.accountService = new AccountServiceImpl();
        this.mapper = DataMapper.getInstance();
    }
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerAccount(String account){
        try {
            Account ac = this.mapper.readValue(account, Account.class);
            this.accountService.registerAccount(ac);
            return this.mapper.writeValueAsString(ac);
        } catch (AccountAlreadyExistsException ex) {
            return this.mapper.getObjectAsJson(new HttpErrorResponse(
                    ex.getMessage(),
                    "1"
            ));
        }catch(IOException ex){
            return this.mapper.getObjectAsJson(new HttpErrorResponse(
                    ex.getMessage(),
                    "0"
            ));
        } catch (AccountInvalidException ex) {
            return this.mapper.getObjectAsJson(new HttpErrorResponse(
                    ex.getMessage(),
                    "2"
            ));
        }
    }
}
