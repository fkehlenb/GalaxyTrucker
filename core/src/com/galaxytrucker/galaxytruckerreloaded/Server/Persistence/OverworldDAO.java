package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateOverworldException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.OverworldNotFoundException;
import lombok.NonNull;


/** Manages the overworld objects in the database */
public class OverworldDAO extends ObjectDAO<Overworld> {

    /** Instance */
    private static OverworldDAO instance = null;

    /** Get the DAO instance */
    public static OverworldDAO getInstance(){
        if (instance == null){
            instance = new OverworldDAO();
        }
        return instance;
    }

    /**
     * Add a new OverWorld to the database
     *
     * @param o - the overworld to add to the database
     * @throws DuplicateOverworldException if the OverWorld already exists in the database
     */
    @Override
    public void persist(Overworld o) throws DuplicateOverworldException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DuplicateOverworldException();
        }
    }

    /** Update overworld
     * @param o - the overworld to update
     * @throws OverworldNotFoundException if it cannot be found in the database */
    public void update(Overworld o) throws OverworldNotFoundException{
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new OverworldNotFoundException();
        }
    }

    /**
     * Remove an existing OverWorld from the database
     *
     * @param o - the OverWorld to remove
     * @throws OverworldNotFoundException if the OverWorld cannot be found in the database
     */
    @Override
    public void remove(Overworld o) throws OverworldNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new OverworldNotFoundException();
        }
    }

    /** Get the overworld of a designated user
     * @param username - the user whose world map to get
     * @return the user's world map
     * @throws OverworldNotFoundException if the overworld couldn't be found */
    public Overworld getOverworldByUser(String username) throws OverworldNotFoundException{
        try {
            entityManager.getTransaction().begin();
            @NonNull Overworld o = entityManager.createNamedQuery("Overworld.getByUsername",Overworld.class).setParameter("name",username).getSingleResult();
            entityManager.getTransaction().commit();
            return o;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new OverworldNotFoundException();
        }
    }
}
