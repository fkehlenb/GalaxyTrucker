package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRoomException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RoomNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;

public class SystemService {

    /**
     * System dao
     */
    private RoomDAO roomDAO;

    /**
     * Add a new system
     *
     * @param s - the system to add
     * @throws DuplicateRoomException if the system already exists
     */
    public void addSystem(System s) throws DuplicateRoomException {
    }

    /**
     * Remove a system
     *
     * @param s - the system to remove
     * @throws RoomNotFoundException if the system cannot be found
     */
    public void removeSystem(System s) throws RoomNotFoundException {
    }
}
