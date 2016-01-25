package com.robertnorthard.dtbs.server.layer.controllers;

import com.robertnorthard.dtbs.server.exceptions.AccountAlreadyExistsException;
import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.exceptions.AccountInvalidException;
import com.robertnorthard.dtbs.server.exceptions.AccountNotFoundException;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.persistence.dto.HttpResponse;
import com.robertnorthard.dtbs.server.layer.services.AccountService;
import com.robertnorthard.dtbs.server.layer.services.AccountServiceImpl;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
/**
 * Account controller for managing external interactions with data model.
 * TODO: Add generic HTTP response and status codes.
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
    
    /**
     * Register new account.
     * @param account JSON representation of an account.
     * @return account object if successful else an appropriate error message:
     *  - IOException - invalid JSON
     *  - AccountAlreadyExistsException - account with username already exists.
     *  - AccountInvalidException - invalid account details.
     */
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
            return this.mapper.getObjectAsJson(new HttpResponse(
                    ex.getMessage(),
                    "1"
            ));
        }catch(IOException ex){
            return this.mapper.getObjectAsJson(new HttpResponse(
                    ex.getMessage(),
                    "0"
            ));
        } catch (AccountInvalidException ex) {
            return this.mapper.getObjectAsJson(new HttpResponse(
                    ex.getMessage(),
                    "2"
            ));
        }
    }
    
    /**
     * Reset account password.
     * @param username inferred from path parameter.
     * @return account reset confirmation.
     * @throws AccountNotFoundException account with username does not exist.
    */
    @POST
    @Path("/{username}/reset")
    @Produces(MediaType.APPLICATION_JSON)
    public String resetAccount(@PathParam("username") String username) 
            throws AccountNotFoundException{
        
        try{
            this.accountService.resetPassword(username);
            
           return this.mapper.getObjectAsJson(new HttpResponse(
                   "Password reset sent.",
                    "0"
            ));
            
        } catch (AccountNotFoundException ex) {
            
            return this.mapper.getObjectAsJson(new HttpResponse(
                    ex.getMessage(),
                    "1"
            ));
        }
    }
    

    @POST
    @Path("/{username}/reset/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public String resetPassword(@PathParam("username") String username, @PathParam("code") String code, String message) {
        
        try {
            JSONObject object = new JSONObject(message);
          
            this.accountService.resetPassword(code,username,object.getString("password"));
    
            return this.mapper.getObjectAsJson(new HttpResponse(
                    "Password change successful",
                    "0"
            ));
           
        } catch (JSONException ex) {
             return this.mapper.getObjectAsJson(new HttpResponse(
                    ex.getMessage(),
                    "1"
            ));
        }catch (AccountAuthenticationFailed ex) {
             return this.mapper.getObjectAsJson(new HttpResponse(
                    ex.getMessage(),
                    "2"
            ));
        } catch (AccountNotFoundException ex) {
             return this.mapper.getObjectAsJson(new HttpResponse(
                    ex.getMessage(),
                    "3"
            ));
        }
    }
}
