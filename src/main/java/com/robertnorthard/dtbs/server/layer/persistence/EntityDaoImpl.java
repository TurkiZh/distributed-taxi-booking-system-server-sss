package com.robertnorthard.dtbs.server.layer.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Generic Entity Data Access Object implementation for managing entities.
 * @author robertnorthard
 * @param <K> unique identifier for entity
 * @param <V> type of object to manipulate
 */
public class EntityDaoImpl<K,V> implements EntityDao<K,V> {

    private static final Logger LOGGER = Logger.getLogger(EntityDaoImpl.class.getName());
    
    private final Class<V> persistentClass;
    
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("com.robertnorthard.dtms.server_DTMSServer_war_1.0-SNAPSHOTPU");
    private final EntityManager em  = factory.createEntityManager();
        
    
    /**
     * Default constructor for class EntityDaoImpl
    */
    public EntityDaoImpl(){
        
        // Source - http://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime
        this.persistentClass = (Class<V>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[1];
    }
    
    /**
     * Find a return object of type V with primary key K.
     * @param id primary key.
     * @return return object of type V with primary key K.
     */
    @Override
    public V findEntityById(K id)  {
        return this.em.find(persistentClass, id);
    }

    /**
     * Persist an entity to the data layer.
     * @param entity entity to persist
     */
    @Override
    public void persistEntity(V entity){
            em.getTransaction().begin();
            this.em.persist(entity);
            em.getTransaction().commit();
    }

    /**
     * Delete entity by id.
     * @param id id of entity
     */
    @Override
    public void deleteEntityById(K id) {
        V entity = this.findEntityById(id);
        this.em.remove(entity);
    }

    /**
     * Update entity in JPA repository.
     * @param entity entity to update.
     */
    @Override
    public void update(V entity) {
        em.getTransaction().begin();
        this.em.merge(entity);
        em.getTransaction().commit();
    }
}
