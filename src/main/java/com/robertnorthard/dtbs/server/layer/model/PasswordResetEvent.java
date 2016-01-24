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
    private boolean used;
    private DateTime createdAt;
    private DateTime expiry;

    public PasswordResetEvent() {}
    
    public PasswordResetEvent(String username,String code,DateTime expiry){
        this.username = username;
        this.code = code;
        this.expiry = expiry;
        
        this.createdAt = new DateTime();
        this.used = false;
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
    public void setUsed(){
        this.used = true;
    }
    
    /**
     * @return true if reset code used, else false.
     */
    public boolean isUsed(){
        return this.used;
    }
}
