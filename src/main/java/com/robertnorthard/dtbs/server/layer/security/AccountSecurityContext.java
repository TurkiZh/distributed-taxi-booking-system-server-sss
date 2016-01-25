package com.robertnorthard.dtbs.server.layer.security;

import com.robertnorthard.dtbs.server.layer.model.Account;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 * Security context wrapper class account.
 * Inspiration from: https://simplapi.wordpress.com/2015/09/19/jersey-jax-rs-securitycontext-in-action/
 * @author robertnorthard
 */
public class AccountSecurityContext implements SecurityContext {

    private final Account account;
    
    /**
     * Constructor for class UserSecurityContext.
     * @param account account to wrap
     */
    public AccountSecurityContext(Account account){
        this.account = account;
    }
    /**
     * Returns a java.security.Principal object containing the name of the current 
     * authenticated user. If the user has not been authenticated, the method returns null.
     * @return a java.security.Principal object containing the name of the current 
     * authenticated user. If the user has not been authenticated, the method returns null.
     */
    @Override
    public Principal getUserPrincipal() {
        return new Principal() {

            @Override
            public String getName() {
                return account.getUsername();
            }
        };
    }

     /**
     * Returns a boolean indicating whether the authenticated user is included in the specified logical "role".
     * @param role role to confirm.
     * @return a boolean indicating whether the authenticated user is included in the specified logical "role".
     */
    @Override
    public boolean isUserInRole(String role) {
        return this.account.hasRole(role);
    }

    /**
     * Returns a boolean indicating whether this request was made using a secure channel, such as HTTPS.
     * @return a boolean indicating whether this request was made using a secure channel, such as HTTPS.
     */
    @Override
    public boolean isSecure() {
        return false;
    }


    /**
     * Returns the string value of the authentication scheme used to protect the resource.
     * @return the string value of the authentication scheme used to protect the resource.
     */
    @Override
    public String getAuthenticationScheme() {
      return SecurityContext.BASIC_AUTH;
    }
}
