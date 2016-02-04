package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;
import java.util.List;

/**
 * A generic Data Access Object (DAO) interface for  
 * handling and managing event related data
 * requested, updated, and processed in the application and maintained in the
 * database.
 * 
 * @author robertnorthard
 * @param <K> primary key for entity
 * @param <V> type of entity to manipulate
 */
public interface EntityDao<K,V> {
    
    /**
     * Find a return object of type V with primary key K.
     * 
     * @param id primary key.
     * @return return object of type V with primary key K.
     * @throws EntityNotFoundException entity not found exception.
     * @throws IllegalArgumentException if id is null.
     */
    public V findEntityById(final K id) throws EntityNotFoundException;
    
    /**
     * Persist an entity to the data layer.
     * 
     * @param entity entity to persist
     * @throws IllegalArgumentException if entity is null.
     */
    public void persistEntity(final V entity);

    /**
     * Delete entity by id. 
     * 
     * @param id id of entity
     * @throws EntityNotFoundException entity not found exception.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteEntityById(final K id) throws EntityNotFoundException;
    
    /**
     * Update entity in JPA repository.
     * 
     * @param entity entity to update.
     * @throws IllegalArgumentException if entity is null.
     */
    public void update(final V entity);
     
    /**
     * Return all entities for given class.
     * 
     * @return all entities for given class.
     */
    public List<V> findAll();
}