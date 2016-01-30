package com.robertnorthard.dtbs.server.layer.utils;

/**
 * Observer interface implementation for the Observer pattern.
 * @author robertnorthard
 */
public interface Observer {
    
    /**
     * Method called whenever subject state changes.
     * @param obj updated event.
     */
    public void update(Object obj);
    
}
