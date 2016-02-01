package com.robertnorthard.dtbs.server.layer.persistence;

import com.robertnorthard.dtms.server.common.model.Taxi;


/**
 * A taxi Data Access Object (DAO) class for handling and managing event related data
 * requested, updated, and processed in the application and maintained in the
 * database.
 * 
 * @author robertnorthard
 */
public class TaxiDao extends JpaEntityDaoImpl<Long,Taxi> {}
