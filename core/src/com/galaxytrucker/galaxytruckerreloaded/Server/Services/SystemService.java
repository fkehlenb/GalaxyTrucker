package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRoomException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RoomNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;

import java.util.List;

/**
 * Manages ship systems actions
 */
@SuppressWarnings("Duplicates")
public class SystemService {

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * RoomDAO
     */
    private RoomDAO roomDAO = RoomDAO.getInstance();

    // Todo cameras and cockpit cannot remove energy, max level 3, always max energy

    /**
     * Add energy to a system
     *
     * @param ship   - the client's ship
     * @param system - the system to add energy to
     * @param amount - the amount of energy to add
     */
    public ResponseObject addEnergy(Ship ship, System system, int amount) {
        ResponseObject responseObject = new ResponseObject();
        try {
            ship = shipDAO.getById(ship.getId());
            system = (System) roomDAO.getById(system.getId());

            // Todo implement me

            List<Room> rooms = ship.getSystems();
            for (Room r : rooms) {
                if (r.getId() == system.getId()) {
                    rooms.set(rooms.indexOf(r), system);
                }
            }
            ship.setSystems(rooms);
            roomDAO.update(system);
            shipDAO.update(ship);
            responseObject.setValidRequest(true);
            responseObject.setResponseShip(ship);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Remove energy from a system
     *
     * @param ship   - the client's ship
     * @param system - the system to remove energy from
     * @param amount - the amount of energy to remove
     */
    public ResponseObject removeEnergy(Ship ship, System system, int amount) {
        ResponseObject responseObject = new ResponseObject();
        try {
            ship = shipDAO.getById(ship.getId());
            system = (System) roomDAO.getById(system.getId());

            // Todo implement me

            List<Room> rooms = ship.getSystems();
            for (Room r : rooms) {
                if (r.getId() == system.getId()) {
                    rooms.set(rooms.indexOf(r), system);
                }
            }
            ship.setSystems(rooms);
            roomDAO.update(system);
            shipDAO.update(ship);
            responseObject.setValidRequest(true);
            responseObject.setResponseShip(ship);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Upgrade a system
     *
     * @param ship   - the client's ship
     * @param system - the system to upgrade
     */
    public ResponseObject upgradeSystem(Ship ship, System system) {
        ResponseObject responseObject = new ResponseObject();
        try {
            ship = shipDAO.getById(ship.getId());
            system = (System) roomDAO.getById(system.getId());

            // Todo implement me

            List<Room> rooms = ship.getSystems();
            for (Room r : rooms) {
                if (r.getId() == system.getId()) {
                    rooms.set(rooms.indexOf(r), system);
                }
            }
            ship.setSystems(rooms);
            roomDAO.update(system);
            shipDAO.update(ship);
            responseObject.setValidRequest(true);
            responseObject.setResponseShip(ship);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }
}
