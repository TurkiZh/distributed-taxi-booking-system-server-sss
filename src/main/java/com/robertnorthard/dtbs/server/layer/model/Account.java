package com.robertnorthard.dtbs.server.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity class for a user.
 * @author robertnorthard
 */
@Entity
@Table(name="Account")
public class Account implements Serializable {
  
    @Id @Column(name="USERNAME")
    private String username; 
    
    @NotNull
    @Column(name="NAME")
    private String name;
    
    @NotNull
    @Column(name="PASSWORD")
    private String password;
    
    @NotNull
    @Column(name="EMAIL")
    private String email;
    
    @NotNull
    @Column(name="PHONE_NUMBER")
    private String phoneNumber;
    
    @Column(name="ROLE")
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @Column(name="ACTIVE")
    @Enumerated(EnumType.STRING)
    private AccountStatus active;
    
    public Account() {
        // Empty as per JPA 2.0 specification.
    }
     
    /** 
     * Constructor for account.
     * @param username username.
     * @param name name.
     * @param password password hash.
     * @param phoneNumber phone number.
     * @param email email.
     */
    public Account(String username, String name, String password, String phoneNumber, String email){    
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        
        this.setActive();
    }
    
    /**
     * Return true if provided password matches hash else false.
     * @param password password to check. 
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
     * Check if a user has a specified role.
     * 
     * @param role role to check. Role can be provided in upper or lowercase. 
     * @return true if user has role else false.
     */
    public boolean hasRole(String role){
        try{
            return this.role == AccountRole.valueOf(role.toUpperCase());
        }catch(IllegalArgumentException ex){
            return false;
        }
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
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the role
     */
    public AccountRole getRole() {
        return role;
    }
    
   /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @param role the role to set
     */
    public void setRole(AccountRole role) {
        if(role == null){
            throw new IllegalArgumentException("Role cannot be null.");
        }
        
        this.role = role;
    }
    
    /**
     * @return Return true if active, else false.
     */
    public boolean isActive(){
        return this.active.ACTIVE == AccountStatus.ACTIVE;
    }
    
    /**
     * Set account inactive.
     */
    public void setInActive(){
        this.active = AccountStatus.ACTIVE;
    }
    
    /**
     * Set account active.
     */
    public final void setActive(){
        this.active = AccountStatus.ACTIVE;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        
        Account other = (Account) object;
        
        return !((this.username == null && other.username != null) 
                || (this.username != null
                && !this.username.equals(other.username)));
    }
    
  /**
     * Return true if username is regex (([A-Z]|[a-z])*[0-9]) matches else false.
     * 
     * @param username username to match.
     * @return true if username is regex (([A-Z]|[a-z])*[0-9]) matches else false.
     */
    public static boolean isValidUsername(String username){
        
        if(username == null){
            throw new IllegalArgumentException("Username cannot be null.");
        }
        
        return java.util.regex.Pattern.matches("^[a-zA-Z].{5,16}$", username);
    }
}
