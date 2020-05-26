package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateOverworldException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.OverworldNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ObjectDAO;

public class OverworldDAO extends ObjectDAO<Overworld> {

    /** Add a new OverWorld to the database
     * @param o - the overworld to add to the database
     * @throws DuplicateOverworldException if the OverWorld already exists in the database */
    @Override
    public void persist(Overworld o) throws DuplicateOverworldException {

    }

    /** Remove an existing OverWorld from the database
     * @param o - the OverWorld to remove
     * @throws OverworldNotFoundException if the OverWorld cannot be found in the database */
    @Override
    public void remove(Overworld o) throws OverworldNotFoundException {

    }
}
