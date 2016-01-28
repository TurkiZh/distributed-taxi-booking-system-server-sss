package com.robertnorthard.dtbs.server.layer.security;

import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.services.AccountService;
import com.robertnorthard.dtbs.server.layer.services.AccountServiceImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * Authenticate a user using roles.
 * @author robertnorthard
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{
    
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());
    
    AccountService accountService = new AccountServiceImpl();
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");
        String requestUri = requestContext.getUriInfo().getAbsolutePath().toString();
        
        Account account = null;
        
        try {
            if(authHeader==null){
                throw new AccountAuthenticationFailed();
            }
            
            account = this.accountService.authenticate(authHeader);
            requestContext.setSecurityContext(new AccountSecurityContext(account, requestUri));
        } catch (AccountAuthenticationFailed ex) {
            LOGGER.log(Level.INFO, "User not authenticated", "");
        }
    } 
}
