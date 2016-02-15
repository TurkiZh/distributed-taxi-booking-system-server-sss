package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtbs.server.common.exceptions.EntityNotFoundException;
import com.robertnorthard.dtbs.server.layer.utils.EntityManagerFactoryUtils;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;

/**
 * A generic Data Access Object (DAO) class for interfacing with Java
 * Persistence API, handling and managing event related data requested, updated,
 * and processed in the application and maintained in the database.
 *
 * @author robertnorthard
 * @param <K> unique identifier for entity
 * @param <V> type of object to manipulate
 */
public class JpaEntityDaoImpl<K, V> implements EntityDao<K, V> {

    private final Class<V> persistentClass;

    /**
     * Default constructor for class EntityDaoImpl
     */
    public JpaEntityDaoImpl() {
        this.persistentClass = (Class<V>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[1];
    }

    /**
     * Return a persistent context entity manager.
     *
     * @return persistent context entity manager.
     */
    public EntityManager getEntityManager() {
        return EntityManagerFactoryUtils.getEntityManager();
    }

    /**
     * Find a return object of type V with primary key K.
     * If entity with key K is not found return null.
     *
     * @param id primary key.
     * @return return object of type V with primary key K. 
     *         If entity with key K is not found return null.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public V findEntityById(K id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        
        V foundEntity = null;
        EntityManager em = this.getEntityManager();
        
        try{
           foundEntity = em.find(persistentClass, id);
        }finally{
            if(em.isOpen()){
                em.close();
            }
        }
        return foundEntity;        
    }

    /**
     * Persist an entity to the data layer.
     *
     * @param entity entity to persist
     * @throws IllegalArgumentException if entity is null.
     */
    @Override
    public void persistEntity(V entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(entity);
            em.flush();
        } finally {
            if (!tx.getRollbackOnly()) {
                tx.commit();
            }else if(em.isOpen()){
                em.close();
            }
        }
    }

    /**
     * Delete entity by id.
     *
     * @param id id of entity
     * @throws IllegalArgumentException if id is null.
     * @throws EntityNotFoundException exception if entity was not found.
     */
    @Override
    public void deleteEntityById(K id) throws EntityNotFoundException {

        if (id == null) {
            throw new IllegalArgumentException("id cannot be null.");
        }

        EntityManager em = this.getEntityManager();

        V entity = em.find(persistentClass, id);

        if (!(entity != null)) {
            try {
                em.remove(entity);
            } finally {
                if (em.isOpen()) {
                    em.close();
                }
            }
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Update entity in JPA repository by merging attributes.
     *
     * @param entity entity to update.
     * @throws IllegalArgumentException if entity is null.
     */
    @Override
    public void update(V entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(entity);
            em.flush();
        } finally {
            if (!tx.getRollbackOnly()) {
                tx.commit();
            } else if (em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Return all entities for given class.
     * If no entities found, null is returned.
     *
     * @return all entities for given class. 
     *         If no entities, found null is returned.
     */
    @Override
    public List<V> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(this.persistentClass));

        List<V> results = null;
        EntityManager em = this.getEntityManager();

        try {
            results = em.createQuery(cq).getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return results;
    }
}
