package com.robertnorthard.dtbs.server.layer.persistence.dto;

/**
 * A class to encapsulate objects in a http response.
 *
 * @author robertnorthard
 */
public class HttpObjectResponse extends HttpResponse {

    private Object data;

    public HttpObjectResponse(Object object, String status) {
        super(status);

        this.data = object;
    }

    /**
     * @return the object
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the object to set
     */
    public void setData(Object data) {
        this.data = data;
    }
}
