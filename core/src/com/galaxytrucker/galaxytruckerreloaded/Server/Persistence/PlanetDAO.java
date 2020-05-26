package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicatePlanetException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.PlanetNotFoundException;

public class PlanetDAO extends ObjectDAO<Planet> {

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
     * Remove an existing planet from the database
     *
     * @param p - the planet to remove
     * @throws PlanetNotFoundException if the planet cannot be found in the database
     */
    @Override
    public void remove(Planet p) throws PlanetNotFoundException {

    }
}
