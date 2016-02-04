
package com.robertnorthard.dtbs.server.layer.persistence.dto;

import java.util.List;

/**
 * Data transfer object 
 * encapsulate a http response that contains a collection of data.
 * 
 * @author robertnorthard
 */
public class HttpListResponse<T> extends HttpResponse{

    private List<T> data;

    public HttpListResponse(List<T> data, String status){
        super(status);
        this.data = data;
    }
    
    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }
}
