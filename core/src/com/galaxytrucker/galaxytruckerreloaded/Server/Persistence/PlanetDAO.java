package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicatePlanetException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.PlanetNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Manages the planet objects in the database */
public class PlanetDAO extends ObjectDAO<Planet> implements Serializable {

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

    /** Get a planet using its id
     * @param id - the planet id
     * @return the planet with a matching id
     * @throws PlanetNotFoundException if the planet with the requested id does not exist */
    public Planet getById(int id) throws PlanetNotFoundException{
        try {
            Planet p = null;
            entityManager.getTransaction().begin();
            p = entityManager.createNamedQuery("Planet.getById",Planet.class).setParameter("id",id).getSingleResult();
            entityManager.getTransaction().commit();
            if (p==null){
                throw new NullPointerException();
            }
            return p;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new PlanetNotFoundException();
        }
    }

    /** Get a planet using its name
     * @param name - the planet name
     * @return the planet with a matching name
     * @throws PlanetNotFoundException if the planet cannot be found in the database */
    public List<Planet> getByName(String name) throws PlanetNotFoundException{
        try {
            List<Planet> planets = new ArrayList<>();
            entityManager.getTransaction().begin();
            planets = entityManager.createNamedQuery("Planet.getByName",Planet.class).setParameter("name",name).getResultList();
            entityManager.getTransaction().commit();
            if (planets.isEmpty()){
                throw new NullPointerException();
            }
            return planets;
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
