package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateShipException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.ShipNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

public class ShipDAO extends ObjectDAO<Ship> {

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
    @Override
    public void persist(Ship s) throws DuplicateShipException {

    }

    /** Update a ship in the database
     * @param s - the ship to update
     * @throws ShipNotFoundException if the ship cannot be found in the database */
    public void update(Ship s) throws ShipNotFoundException{

    }

    /**
     * Get a ship from the database using its associated user
     *
     * @param user - the ship's associated user
     */
    private Ship getShipByUser(String user) throws ShipNotFoundException, UserNotFoundException {
        return null;
    }

    /**
     * Remove a ship from the database
     *
     * @param s - the ship to remove
     */
    @Override
    public void remove(Ship s) throws ShipNotFoundException{

    }
}
