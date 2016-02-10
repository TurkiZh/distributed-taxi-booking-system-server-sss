package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtbs.server.layer.model.taxi.Taxi;
import com.robertnorthard.dtbs.server.layer.model.taxi.TaxiState;
import java.util.List;
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
    
    /**
     * Return collection of taxis on duty but not on a job.
     * 
     * @return a collection of taxis on duty but not on a job.
     */
    public List<Taxi> findAllOnDuty(){
        return this.findTaxiWithState(Taxi.getOnDutyTaxiState());
    }
    
   /**
     * Return collection of taxis off duty.
     * 
     * @return a collection of taxis that are off duty.
     */
    public List<Taxi> findAllOffDuty(){
        return this.findTaxiWithState(Taxi.getOffDutyTaxiState());
    }
    
    /**
     * Find taxi with the specified state.
     * 
     * @param state taxis with state.
     * @return a collection. of taxis with the provided state.
     */
    private List<Taxi> findTaxiWithState(TaxiState state){
        EntityManager em = this.getEntityManager();
        Query query = em.createNamedQuery("Taxi.findTaxisWithState", Taxi.class);
        query.setParameter("state", state);
        return query.getResultList();
    }
}
