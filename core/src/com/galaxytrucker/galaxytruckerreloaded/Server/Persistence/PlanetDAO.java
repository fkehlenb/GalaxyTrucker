package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicatePlanetException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.PlanetNotFoundException;
import com.j256.ormlite.dao.Dao;

public class PlanetDAO extends ObjectDAO<Planet> {

    /** Planet DAO */
    private Dao<Planet,String> planetDAO;

    /**
     * Add a new planet to the database
     *
     * @param p - the planet to add
     * @throws DuplicatePlanetException if the planet already exists in the database
     */
    @Override
    public void persist(Planet p) throws DuplicatePlanetException {

    }

    /**
     * Update an existing planet in the database
     *
     * @param p - the planet to update
     * @throws PlanetNotFoundException if the planet cannot be found in the database
     */
    public void update(Planet p) throws PlanetNotFoundException {

    }

    /**
     * Remove an existing planet from the database
     *
     * @param p - the planet to remove
     * @throws PlanetNotFoundException if the planet cannot be found in the database
     */
    @Override
    public void remove(Planet p) throws PlanetNotFoundException {

    }
}
