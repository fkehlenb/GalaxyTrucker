package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRoomException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RoomNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;

/** Manages ship systems actions */
public class SystemService {

    /** ShipDAO */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /** RoomDAO */
    private RoomDAO roomDAO = RoomDAO.getInstance();

    /** Add energy to a system
     * @param ship - the client's ship
     * @param system - the system to add energy to
     * @param amount - the amount of energy to add */
    public ResponseObject addEnergy(Ship ship,System system,int amount){
        return null;
    }

    /** Remove energy from a system
     * @param ship - the client's ship
     * @param system - the system to remove energy from
     * @param amount - the amount of energy to remove */
    public ResponseObject removeEnergy(Ship ship,System system,int amount){
        return null;
    }

    /** Upgrade a system
     * @param ship - the client's ship
     * @param system - the system to upgrade */
    public ResponseObject upgradeSystem(Ship ship,System system){
        return null;
    }
}
