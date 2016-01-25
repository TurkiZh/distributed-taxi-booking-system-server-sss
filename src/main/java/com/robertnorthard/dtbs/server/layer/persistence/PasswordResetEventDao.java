package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtbs.server.layer.model.PasswordResetEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Password Reset Event Data Access Object.
 * @author robertnorthard
 */
public class PasswordResetEventDao extends EntityDaoImpl<Long,PasswordResetEvent> {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("com.robertnorthard.dtms.server_DTMSServer_war_1.0-SNAPSHOTPU");
    private final EntityManager em  = factory.createEntityManager();
        
    /**
     * Find password reset code using username, code with status of active.
     * Return list as there is a possibility user reset their account multiple times and an 
     * identical reset code is generated.
     * @param username username to search by.
     * @param code reset code to search by.
     * @return collection of PasswordResetEvent with matching username and code.
     */
    public List<PasswordResetEvent> findPasswordResetByUsernameAndCode(String username, String code){
        Query query = em.createNativeQuery("SELECT * FROM PASSWORDRESETEVENT WHERE USERNAME = ? AND CODE = ? AND ACTIVE=1", PasswordResetEvent.class);
        query.setParameter(1, username);
        query.setParameter(2, code); 
        return query.getResultList();
    }
    
    /**
     * Return a collection of all active password resets for a given user.
     * @param username username to search for active password resets.
     * @return a collection of all active password resets for a given user.
     */
    public List<PasswordResetEvent> findActivePasswordResetByUsername(String username){
        Query query = em.createNativeQuery("SELECT * FROM PASSWORDRESETEVENT WHERE USERNAME = ?", PasswordResetEvent.class);
        query.setParameter(1, username);
        return query.getResultList();
    }
}
