package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicatePlanetException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.PlanetNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;

public class PlanetService {

    /**
     * Planet DAO
     */
    private PlanetDAO planetDAO;

    /**
     * Add a new planet
     *
     * @param p - the planet to add
     * @throws DuplicatePlanetException if the planet already exists
     */
    public void addPlanet(Planet p) throws DuplicatePlanetException {
    }

    /**
     * Edit an existing planet
     *
     * @param p - the planet to edit
     * @throws PlanetNotFoundException if the planet cannot be found
     */
    public void editPlanet(Planet p) throws PlanetNotFoundException {
    }

    /**
     * Remove an existing planet
     *
     * @param p - the planet to remove
     * @throws PlanetNotFoundException if the planet cannot be found
     */
    public void removePlanet(Planet p) throws PlanetNotFoundException {
    }
}
