package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

public class ShipDAO extends ObjectDAO<Ship> {

    /**
     * Database connection source
     */
    private ConnectionSource source;

    /**
     * ShipDAO
     */
    private Dao<Ship, String> shipDAO;

    /**
     * Constructor
     *
     * @param source - the database connection source
     */
    public ShipDAO(ConnectionSource source) {

    }


    /**
     * Add a new ship to the database
     *
     * @param s - the ship to add
     */
    public void persist(Ship s) {

    }

    /**
     * Get a ship from the database using its associated user
     *
     * @param user - the ship's associated user
     */
    private Ship getShipByUser(String user) {
        return null;
    }

    /**
     * Remove a ship from the database
     *
     * @param s - the ship to remove
     */
    public void remove(Ship s) {

    }
}
