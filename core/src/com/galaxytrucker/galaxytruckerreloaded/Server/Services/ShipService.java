package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateShipException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.ShipNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ObjectDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.j256.ormlite.support.ConnectionSource;

public class ShipService {

    /**
     * The DAO used by the service
     */
    private ShipDAO shipDAO;

    /**
     * Add a new ship to the database
     *
     * @param s - the ship to add
     *
     * @throws DuplicateShipException if the ship already exists in the database
     */
    public void persist(Ship s) throws DuplicateShipException {
    }

    /** Update a ship in the database
     * @param s - the ship to update \
     * @throws ShipNotFoundException if the ship cannot be found in the database */
    public void update(Ship s) throws ShipNotFoundException{

    }

    /**
     * Fetch a ship from the database using the user associated to it
     *
     * @param u - the user associated to the ship
     *
     * @throws UserNotFoundException if the user cannot be found in the database
     */
    public User getShipByUser(User u) throws UserNotFoundException {
        return null;
    }

    /**
     * Remove a ship from the database
     *
     * @param s - the ship to remove from the database
     */
    public void remove(Ship s) throws ShipNotFoundException {
    }

    /**
     * Constructor
     *
     * @param source - the database connection source
     */
    public ShipService(ConnectionSource source) {
    }

}
