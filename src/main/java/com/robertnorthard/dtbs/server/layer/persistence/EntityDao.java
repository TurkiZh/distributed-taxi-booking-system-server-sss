package com.robertnorthard.dtbs.server.layer.persistence;

/**
 * Generic entity data access object that wraps JPA API
 * for maintaining entities.
 * @author robertnorthard
 * @param <K> primary key for entity
 * @param <V> type of entity to manipulate
 */
public interface EntityDao<K,V> {
    
    /**
     * Find a return object of type V with primary key K.
     * @param id primary key.
     * @return return object of type V with primary key K.
     */
    public V findEntityById(final K id);
    
    /**
     * Persist an entity to the data layer.
     * @param entity entity to persist
     */
    public void persistEntity(final V entity);

    /**
     * Delete entity by id.
     * @param id id of entity
     */
    public void deleteEntityById(final K id);
    
    /**
     * Update entity in JPA repository.
     * @param entity entity to update.
     */
    public void update(final V entity);
}