package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
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
            // Dont trust client data
            ship = shipDAO.getById(ship.getId());
            system = (System) roomDAO.getById(system.getId());
            // Manual verification
            java.lang.System.out.println("\n==================== ACTION ADD ENERGY ====================");
            java.lang.System.out.println("[PRE]:[System]:" + system.getSystemType() + ":[CurrentEnergy]:" + system.getEnergy()
                    + ":[Add-Energy]:" + amount);
            java.lang.System.out.println("[PRE]:[Ship]:"+ship.getId()+":[Energy]:" + ship.getEnergy());
            // Check if system exists in ship
            boolean roomExists = false;
            for (Room r : ship.getSystems()){
                if (r.getId() == system.getId()){
                    roomExists = true;
                    java.lang.System.out.println("[System]:[Exists]");
                }
            }
            // Check for wrong systems
            if (!system.getSystemType().equals(SystemType.COCKPIT)&&!system.getSystemType().equals(SystemType.CAMERAS) && roomExists && system.isUnlocked()) {
                // Check for energy
                if (ship.getEnergy() >= amount && (system.getEnergy() + amount) <= system.getMaxEnergy()) {
                    List<Room> rooms = ship.getSystems();
                    for (Room r : rooms) {
                        if (r.getId() == system.getId()) {
                            // Re-enable system if disabled
                            system.setDisabled(false);
                            // Set new energy levels
                            system.setEnergy(system.getEnergy() + amount);
                            ship.setEnergy(ship.getEnergy()-amount);
                            rooms.set(rooms.indexOf(r), system);
                            java.lang.System.out.println("[System]:[Added-Energy]");
                            break;
                        }
                    }
                    // Update data
                    ship.setSystems(rooms);
                    roomDAO.update(system);
                    shipDAO.update(ship);
                    // Verification
                    java.lang.System.out.println("[POST]:[System]:" + system.getSystemType() + ":[CurrentEnergy]:" + system.getEnergy()
                            + ":[Add-Energy]:" + amount);
                    java.lang.System.out.println("[POST]:[Ship]:"+ship.getId()+":[Energy]:" + ship.getEnergy());
                    java.lang.System.out.println("===========================================================");
                    // Set valid request
                    responseObject.setValidRequest(true);
                    responseObject.setResponseShip(ship);
                }
            }
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
            // Dont trust client data
            ship = shipDAO.getById(ship.getId());
            system = (System) roomDAO.getById(system.getId());
            // Manual verification
            java.lang.System.out.println("==================== ACTION REMOVE ENERGY ====================");
            java.lang.System.out.println("[PRE]:[System]:" + system.getSystemType() + ":[CurrentEnergy]:" + system.getEnergy()
                    + ":[Remove-Energy]:" + amount);
            java.lang.System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[Energy]:" + ship.getEnergy());
            // Check if system exists in ship
            boolean roomExists = false;
            for (Room r : ship.getSystems()) {
                if (r.getId() == system.getId()) {
                    roomExists = true;
                    java.lang.System.out.println("[System]:[Exists]");
                }
            }
            // Check for wrong systems
            if (!system.getSystemType().equals(SystemType.COCKPIT) && !system.getSystemType().equals(SystemType.CAMERAS) && roomExists && system.isUnlocked()) {
                // Check for energy
                if ((system.getEnergy() - amount) >= 0) {
                    List<Room> rooms = ship.getSystems();
                    for (Room r : rooms) {
                        if (r.getId() == system.getId()) {
                            if (system.getEnergy() - amount == 0) {
                                // Disable system if no energy
                                system.setDisabled(true);
                            }
                            // Set new energy levels
                            system.setEnergy(system.getEnergy() - amount);
                            ship.setEnergy(ship.getEnergy()+amount);
                            rooms.set(rooms.indexOf(r), system);
                            java.lang.System.out.println("[System]:[Removed-Energy]");
                            break;
                        }
                    }
                    // Update data
                    ship.setSystems(rooms);
                    roomDAO.update(system);
                    shipDAO.update(ship);
                    // Verification
                    java.lang.System.out.println("[POST]:[System]:" + system.getSystemType() + ":[CurrentEnergy]:" + system.getEnergy()
                            + ":[Add-Energy]:" + amount);
                    java.lang.System.out.println("[POST]:[Ship]:" + ship.getId() + ":[Energy]:" + ship.getEnergy());
                    java.lang.System.out.println("==============================================================");
                    // Valid request
                    responseObject.setValidRequest(true);
                    responseObject.setResponseShip(ship);
                }
            }
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

    /** Install a system
     * @param ship - the client ship
     * @param systemType - the type of system to install */
    public ResponseObject installSystem(Ship ship,SystemType systemType){
        ResponseObject responseObject = new ResponseObject();
        try {

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }
}
