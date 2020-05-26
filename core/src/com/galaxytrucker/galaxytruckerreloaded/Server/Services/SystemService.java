package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateSystemException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.SystemNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.SystemDAO;

public class SystemService {

    /**
     * System dao
     */
    private SystemDAO systemDAO;

    /**
     * Add a new system
     *
     * @param s - the system to add
     * @throws DuplicateSystemException if the system already exists
     */
    public void addSystem(System s) throws DuplicateSystemException {
    }

    /**
     * Remove a system
     *
     * @param s - the system to remove
     * @throws SystemNotFoundException if the system cannot be found
     */
    public void removeSystem(System s) throws SystemNotFoundException {
    }
}
