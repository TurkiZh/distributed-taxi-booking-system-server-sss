package com.robertnorthard.dtbs.server.layer.persistence.dto;

import java.util.Date;

/**
 * HTTP error response data transfer object.
 * @author robertnorthard
 */
public class HttpResponse {
    
    private String message;
    private String code;
    private Date timestamp;
    
    /**
     * 
     * @param message message
     * @param code response code
     */
    public HttpResponse(String message, String code){
        this.message = message;
        this.code = code;
        this.timestamp = new Date();
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}