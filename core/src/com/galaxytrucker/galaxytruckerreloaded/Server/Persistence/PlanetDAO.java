package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicatePlanetException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.PlanetNotFoundException;

/** Manages the planet objects in the database */
public class PlanetDAO extends ObjectDAO<Planet> {

    /** Instance */
    private static PlanetDAO instance = null;

    /** Get the DAO instance */
    public static PlanetDAO getInstance(){
        if (instance == null){
            instance = new PlanetDAO();
        }
        return instance;
    }

    /**
     * Add a new planet to the database
     *
     * @param p - the planet to add
     * @throws DuplicatePlanetException if the planet already exists in the database
     */
    @Override
    public void persist(Planet p) throws DuplicatePlanetException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(p);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DuplicatePlanetException();
        }
    }

    /**
     * Update an existing planet in the database
     *
     * @param p - the planet to update
     * @throws PlanetNotFoundException if the planet cannot be found in the database
     */
    public void update(Planet p) throws PlanetNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(p);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new PlanetNotFoundException();
        }
    }

    /**
     * Remove an existing planet from the database
     *
     * @param p - the planet to remove
     * @throws PlanetNotFoundException if the planet cannot be found in the database
     */
    @Override
    public void remove(Planet p) throws PlanetNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(p);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new PlanetNotFoundException();
        }
    }
}
