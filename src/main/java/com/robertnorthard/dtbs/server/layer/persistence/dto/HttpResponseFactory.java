package com.robertnorthard.dtbs.server.layer.persistence.dto;

import javax.ws.rs.core.Response;

/**
 * A class responsible for generating HTTP response objects.
 * @author robertnorthard
 */
public class HttpResponseFactory {
    
    private static HttpResponseFactory factory;
    
    /**
     * Private constructor as singleton object.
     */
    private HttpResponseFactory(){}
    
    /**
     * Return a shared instance of the HttpResponseFactory. If null create a new instance.
     * @return a shared instance of the HttpResponseFactory.
     */
    public static HttpResponseFactory getInstance(){
        
        if(HttpResponseFactory.factory == null){
            //dead-locking approach to synchronisation.
            synchronized(HttpResponseFactory.class){
                HttpResponseFactory.factory = new HttpResponseFactory();
            }
        }
        
        return HttpResponseFactory.factory;
    }
    
    /**
     * Generate and return a HTTP response corresponding to the specified parametres. 
     * @param object object to encode.
     * @param status HTTP response code.
     * @return return a HTTP response corresponding to the specified parametres.
     */
    public Response getResponse(Object object, Response.Status status){
        
        switch(status){
            case UNAUTHORIZED:
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new HttpResponse(object,"1")
                            .toString()).build();
            case CONFLICT:
                return Response.status(Response.Status.CONFLICT)
                    .entity(new HttpResponse(object,"2")
                            .toString()).build();
                
            case BAD_REQUEST:
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new HttpResponse(object,"3")
                            .toString()).build();
                
            case OK:
                return Response.status(Response.Status.OK)
                    .entity(new HttpResponse(object,"0")
                            .toString()).build();
            case NOT_FOUND:
                return Response.status(Response.Status.OK)
                    .entity(new HttpResponse(object,"4")
                            .toString()).build();
            default:
                return null;
        }        
    }
}
