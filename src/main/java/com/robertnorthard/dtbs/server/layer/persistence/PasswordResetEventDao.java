package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtbs.server.layer.model.events.PasswordResetEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A password reset event Data Access Object (DAO) class for handling and managing event related data
 * requested, updated, and processed in the application and maintained in the
 * database.
 *
 * @author robertnorthard
 */
public class PasswordResetEventDao extends JpaEntityDaoImpl<Long, PasswordResetEvent> {
    
    /**
     * Find password reset code using username, code with status of active.
     * Return list as there is a possibility user reset their account multiple
     * times and an identical reset code is generated.
     *
     * @param username username to search by.
     * @param code reset code to search by.
     * @return collection of PasswordResetEvent with matching username and code.
     */
    public List<PasswordResetEvent> findPasswordResetByUsernameAndCode(String username, String code) {

        EntityManager em = this.getEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT * FROM PASSWORD_RESET_EVENT WHERE USERNAME = ? AND CODE = ? AND ACTIVE=1", PasswordResetEvent.class);
            query.setParameter(1, username);
            return query.getResultList();
        } finally {
            if (em != null) {
              //  em.close();
            }
        }
    }

    /**
     * Return a collection of all active password resets for a given user.
     *
     * @param username username to search for active password resets.
     * @return a collection of all active password resets for a given user.
     */
    public List<PasswordResetEvent> findActivePasswordResetByUsername(String username) {
        EntityManager em = this.getEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT * FROM PASSWORD_RESET_EVENT WHERE USERNAME = ?", PasswordResetEvent.class);
            query.setParameter(1, username);
            return query.getResultList();
        } finally {
            if (em != null) {
              //  em.close();
            }
        }
    }
}
