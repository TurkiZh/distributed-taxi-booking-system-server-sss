package com.robertnorthard.dtbs.server.layer.persistence.dto;

import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import java.util.Date;

/**
 * HTTP error response data transfer object.
 *
 * @author robertnorthard
 */
public class HttpResponse {

    private String status;
    private Date timestamp;

    /**
     *
     * @param status status code for response.
     */
    public HttpResponse(String status) {
        this.status = status;
        this.timestamp = new Date();
    }

    /**
     * @return the code
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setCode(String status) {
        this.status = status;
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

    /**
     * Return JSON string representation of the object.
     *
     * @return string representation of the object.
     */
    @Override
    public String toString() {
        return DataMapper.getInstance().getObjectAsJson(this);
    }
}
