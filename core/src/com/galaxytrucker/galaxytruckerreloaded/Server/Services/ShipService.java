package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
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
     */
    public void addShip(Ship s) {
    }

    /**
     * Fetch a ship from the database using the user associated to it
     *
     * @param u - the user associated to the ship
     */
    public User getShipByUser(User u) {
        return null;
    }

    /**
     * Remove a ship from the database
     *
     * @param s - the ship to remove from the database
     */
    public void removeShip(Ship s) {
    }

    /**
     * Constructor
     *
     * @param source - the database connection source
     */
    public ShipService(ConnectionSource source) {
    }

}
