package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateOverworldException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.OverworldNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.OverworldDAO;

public class OverworldService {

    /**
     * DAO
     */
    private OverworldDAO overworldDAO;

    /**
     * Add a new OverWorld
     *
     * @param o - the OverWorld to add
     * @throws DuplicateOverworldException if the OverWorld already exists
     */
    public void addOverworld(Overworld o) throws DuplicateOverworldException {
    }

    /**
     * Remove an existing OverWorld
     *
     * @param o - the OverWorld to remove
     * @throws OverworldNotFoundException if the OverWorld cannot be found
     */
    public void removeOverworld(Overworld o) throws OverworldNotFoundException {
    }
}
