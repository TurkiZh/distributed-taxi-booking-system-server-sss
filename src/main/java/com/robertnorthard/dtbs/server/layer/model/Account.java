package com.robertnorthard.dtbs.server.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class for a user.
 * @author robertnorthard
 */
@Entity
@Table(name="Account")  
public class Account implements Serializable {
  
    @Id
    private String username;
    private String password;
    private String email;
    private List<String> roles;

    public Account() {
        this.roles = new ArrayList<>();
    }
     
    /**
     * Constructor for account.
     * @param username user's username
     * @param password user's password hash
     * @param email user's email
     */
    public Account(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;

        this.roles = new ArrayList<>();
    }
    
    /**
     * Return true if provided password matches hash else false.
     * @param password 
     * @return true if password matches, else false.
     */
    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
    
    /**
     * @return user user's username 
     */
    public String getUsername(){
        return this.username;
    }
    
    /**
     * Add an additional role to the user.
     * @param role role to add
     */
    public void addRole(String role){
        this.roles.add(role);
    }
    
    /**
     * Remove a role from.
     * @param role role to remove
     */
    public void removeRole(String role){
        this.roles.remove(role);
    }
    
    /**
     * Check if a user has a specified role.
     * @param role role to check.
     * @return true if user has role else false.
     */
    public boolean hasRole(String role){
        return this.roles.contains(role);
    }
    
    /**
     * Return collection of the user's roles.
     * @return a collection of the user's roles.
     */
    public List<String> getRoles(){
        return this.roles;
    }
    
    /**
     * Return user password;
     * @return user password;
     */
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }
   
    /**
     * Set user password.
     * @param password password. 
     */
    @JsonProperty("password")
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Return user's email.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user's email.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
