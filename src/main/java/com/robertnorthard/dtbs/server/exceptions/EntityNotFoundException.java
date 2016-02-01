package com.robertnorthard.dtbs.server.exceptions;

/**
 * Entity not found exception.
 * @author robertnorthard
 */
public class EntityNotFoundException extends Exception {
     public EntityNotFoundException(){
        super("Entity not found");
    }
}
