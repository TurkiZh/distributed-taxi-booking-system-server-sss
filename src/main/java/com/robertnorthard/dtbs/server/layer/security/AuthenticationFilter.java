package com.robertnorthard.dtbs.server.layer.security;

import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.services.AccountService;
import com.robertnorthard.dtbs.server.layer.services.AccountServiceImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * Authenticate a user using roles.
 * @author robertnorthard
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter{
    
    AccountService accountService = new AccountServiceImpl();
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");
   
        Account account;
        
        try {
            if(authHeader==null) throw new AccountAuthenticationFailed();
            
            authHeader = authHeader.replaceFirst("[Bb]asic ", "");
            account = this.accountService.authenticate(authHeader);
            requestContext.setSecurityContext(new AccountSecurityContext(account));
            
        } catch (AccountAuthenticationFailed ex) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
