package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtbs.server.layer.model.Account;

/**
 * A account Data Access Object (DAO) class for handling and managing event
 * related data requested, updated, and processed in the application and
 * maintained in the database.
 *
 * @author robertnorthard
 */
public class AccountDao extends JpaEntityDaoImpl<String, Account> {
}
