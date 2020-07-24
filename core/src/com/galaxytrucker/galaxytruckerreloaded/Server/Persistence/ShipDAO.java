package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateShipException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.ShipNotFoundException;

import lombok.NonNull;

import java.io.Serializable;

/** Manages the ship objects in the database */
public class ShipDAO extends ObjectDAO<Ship> implements Serializable {

    /** Instance */
    private static ShipDAO instance = null;

    /** Get the DAO instance */
    public static ShipDAO getInstance(){
        if (instance == null){
            instance = new ShipDAO();
        }
        return instance;
    }

    /**
     * Add a new ship to the database
     *
     * @param s - the ship to add
     */
    @Override
    public void persist(Ship s) throws DuplicateShipException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(s);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DuplicateShipException();
        }
    }

    /** Update a ship in the database
     * @param s - the ship to update
     * @throws ShipNotFoundException if the ship cannot be found in the database */
    public void update(Ship s) throws ShipNotFoundException{
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(s);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ShipNotFoundException();
        }
    }

    /**
     * Get a ship from the database using its associated user
     *
     * @param user - the ship's associated user
     */
    private Ship getShipByUser(String user) throws ShipNotFoundException {
        try {
            entityManager.getTransaction().begin();
            @NonNull Ship s = entityManager.createNamedQuery("Ship.getByUsername",Ship.class).setParameter("username",user).getSingleResult();
            entityManager.getTransaction().commit();
            return s;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ShipNotFoundException();
        }
    }

    /** Get a ship using its id
     * @param id - the ship id
     * @return the ship with a matching id
     * @throws ShipNotFoundException if the ship does not exist */
    public Ship getById(int id) throws ShipNotFoundException{
        try {
            Ship s = null;
            entityManager.getTransaction().begin();
            s = entityManager.createNamedQuery("Ship.getById",Ship.class).setParameter("id",id).getSingleResult();
            entityManager.getTransaction().commit();
            if (s == null){
                throw new NullPointerException();
            }
            return s;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ShipNotFoundException();
        }
    }

    /**
     * Remove a ship from the database
     *
     * @param s - the ship to remove
     */
    @Override
    public void remove(Ship s) throws ShipNotFoundException{
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(s);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ShipNotFoundException();
        }
    }
}
