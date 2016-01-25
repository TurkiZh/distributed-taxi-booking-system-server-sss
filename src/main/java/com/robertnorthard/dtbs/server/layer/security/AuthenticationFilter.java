package com.robertnorthard.dtbs.server.layer.security;

import com.robertnorthard.dtbs.server.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.services.AccountService;
import com.robertnorthard.dtbs.server.layer.services.AccountServiceImpl;
import java.io.IOException;
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
        String requestUri = requestContext.getUriInfo().getAbsolutePath().toString();
        
        Account account = null;
        
        try {
            if(authHeader==null) throw new AccountAuthenticationFailed();
            
            account = this.accountService.authenticate(authHeader);
            requestContext.setSecurityContext(new AccountSecurityContext(account, requestUri));
            
        } catch (AccountAuthenticationFailed ex) {}
    } 
}
