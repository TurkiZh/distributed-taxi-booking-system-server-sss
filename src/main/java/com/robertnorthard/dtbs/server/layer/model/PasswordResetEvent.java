package com.robertnorthard.dtbs.server.layer.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.joda.time.DateTime;

/**
 * Entity class for password reset events.
 * @author robertnorthard
 */
@Entity
@Table(name="PasswordResetEvent") 
public class PasswordResetEvent implements Serializable { 

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String username;
    private String code;
    private boolean active;
    private DateTime createdAt;
    private DateTime expiry;

    /**
     * Needed by JPA
     */
    public PasswordResetEvent() {
    }
    
    public PasswordResetEvent(String username,String code,DateTime expiry){
        this.username = username;
        this.code = code;
        this.expiry = expiry;
        
        this.createdAt = new DateTime();
        this.active = true;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the expiry
     */
    public DateTime getExpiry() {
        return expiry;
    }
    
    /**
     * @return the event creation time
     */
    public DateTime getCreatedAt(){
        return this.createdAt;
    }
    
    /**
     * Set reset code to used. 
     */
    public void setInactive(){
        this.setActive(false);
    }
    
    /**
     * @return true if reset code used, else false.
     */
    public boolean isActive(){
        return this.active;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param active the used to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @param expiry the expiry to set
     */
    public void setExpiry(DateTime expiry) {
        this.expiry = expiry;
    }

    /**
     * Return true if code is valid, has not expired and is active, else false.
     * @param code code to check.
     * @return true if code is valid, has not expired and is active, else false.
     */
    public boolean validateCode(String code) {
        
        return this.code.equals(code) 
                && this.isActive()
                && new DateTime().isBefore(this.expiry);
    }
}
