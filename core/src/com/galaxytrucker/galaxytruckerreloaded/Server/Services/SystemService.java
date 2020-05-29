package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRoomException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RoomNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;

public class SystemService {

    /**
     * System dao
     */
    private RoomDAO roomDAO;

    /**
     * Ship DAO
     */
    private ShipDAO shipDAO;

    /**
     * Validate system install/uninstall
     *
     * @param s - the command
     * @return true if it is valid, else false
     */
    public boolean validateSystemReplacement(String s) {
        return false;
    }

    /**
     * Install a new system on a ship
     *
     * @param ship   - the ship to install the system on
     * @param system - the system to install
     * @param room   - the room to install the system in
     */
    public void installSystem(Ship ship, System system, Room room) {

    }

    /**
     * Uninstall a system on a ship
     *
     * @param ship   - the ship to remove the system from
     * @param system - the system to remove
     */
    public void uninstalledSystem(Ship ship, System system) {

    }
}
