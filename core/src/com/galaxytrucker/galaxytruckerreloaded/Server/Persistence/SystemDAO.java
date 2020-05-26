package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateSystemException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.SystemNotFoundException;

public class SystemDAO extends ObjectDAO<System> {

    /**
     * Add a new system to the database
     *
     * @param s - the system to add
     * @throws DuplicateSystemException if the system already exists in the database
     */
    @Override
    public void persist(System s) throws DuplicateSystemException {

    }

    /**
     * Remove an existing system from the database
     *
     * @param s - the system to remove
     * @throws SystemNotFoundException if the system cannot be found in the database
     */
    @Override
    public void remove(System s) throws SystemNotFoundException {

    }
}
