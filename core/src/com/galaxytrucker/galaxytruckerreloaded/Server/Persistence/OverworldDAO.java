package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateOverworldException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.OverworldNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ObjectDAO;
import com.j256.ormlite.dao.Dao;

public class OverworldDAO extends ObjectDAO<Overworld> {

    /** OverworldDAO */
    private Dao<Overworld,String> overworldDAO;

    /**
     * Add a new OverWorld to the database
     *
     * @param o - the overworld to add to the database
     * @throws DuplicateOverworldException if the OverWorld already exists in the database
     */
    @Override
    public void persist(Overworld o) throws DuplicateOverworldException {

    }

    /**
     * Remove an existing OverWorld from the database
     *
     * @param o - the OverWorld to remove
     * @throws OverworldNotFoundException if the OverWorld cannot be found in the database
     */
    @Override
    public void remove(Overworld o) throws OverworldNotFoundException {

    }

    /** Get the overworld of a designated user
     * @param username - the user whose world map to get
     * @return the user's world map
     * @throws OverworldNotFoundException if the overworld couldn't be found */
    public Overworld getOverworldByUser(String username) throws OverworldNotFoundException{
        return null;
    }
}
