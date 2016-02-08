package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtbs.server.layer.model.Taxi;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A taxi Data Access Object (DAO) class for handling and managing event related
 * data requested, updated, and processed in the application and maintained in
 * the database.
 *
 * @author robertnorthard
 */
public class TaxiDao extends JpaEntityDaoImpl<Long, Taxi> {

    /**
     * Return a collection of bookings in the specified state.
     *
     * @param username username of driver.
     * @return the drivers taxi.
     */
    public Taxi findTaxiForDriver(String username) {
        EntityManager em = this.getEntityManager();
        Query query = em.createNamedQuery("Taxi.findTaxiForDriver", Taxi.class);
        query.setParameter("username", username);
        return (Taxi) query.getSingleResult();
    }
}
