package com.robertnorthard.dtbs.server.layer.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    private EntityManagerFactory factory;
    private EntityManager em;

    private final Class<V> persistentClass;

    /**
     * Default constructor for class EntityDaoImpl
     */
    public JpaEntityDaoImpl() {
        this.persistentClass = (Class<V>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[1];

        this.factory = Persistence.createEntityManagerFactory("com.robertnorthard.dtms.server");
        this.em = factory.createEntityManager();
    }

    /**
     * Return persistent context entity manager.
     *
     * @return persistent context entity manager.
     */
    public EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Find a return object of type V with primary key K.
     *
     * @param id primary key.
     * @return return object of type V with primary key K.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public V findEntityById(K id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return this.getEntityManager().find(persistentClass, id);
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
        this.getEntityManager().getTransaction().begin();
        this.getEntityManager().persist(entity);
        this.getEntityManager().getTransaction().commit();
    }

    /**
     * Delete entity by id.
     *
     * @param id id of entity
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public void deleteEntityById(K id) {

        if (id == null) {
            throw new IllegalArgumentException("id cannot be null.");
        }

        V entity = this.findEntityById(id);
        this.getEntityManager().remove(entity);
    }

    /**
     * Update entity in JPA repository.
     *
     * @param entity entity to update.
     * @throws IllegalArgumentException if entity is null.
     */
    @Override
    public void update(V entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }
        this.getEntityManager().getTransaction().begin();
        this.getEntityManager().merge(entity);
        this.getEntityManager().getTransaction().commit();
    }

    /**
     * Return all entities for given class.
     *
     * @return all entities for given class.
     */
    @Override
    public List<V> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(this.persistentClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

}
