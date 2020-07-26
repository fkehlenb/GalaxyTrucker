package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
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
                if (ship.getEnergy() >= amount && (system.getEnergy() + amount) <= system.getMaxEnergy()) {
                    List<Room> rooms = ship.getSystems();
                    for (Room r : rooms) {
                        if (r.getId() == system.getId()) {
                            // Re-enable system if disabled
                            ((System) r).setDisabled(false);
                            // Set new energy levels
                            ((System) r).setEnergy(system.getEnergy() + amount);
                            // Heal crew in Medbay
                            if (((System) r).getSystemType().equals(SystemType.MEDBAY) && !ship.isInCombat()){
                                for (Crew c : r.getCrew()){
                                    c.setHealth(c.getMaxhealth());
                                }
                            }
                            ship.setEnergy(ship.getEnergy() - amount);
                            rooms.set(rooms.indexOf(r), ((System) r));
                            java.lang.System.out.println("[System]:[Added-Energy]");
                            break;
                        }
                    }
                    // Update data
                    for (Room r : ship.getSystems()){
                        roomDAO.update(r);
                    }
                    ship.setSystems(rooms);
                    shipDAO.update(ship);
                    // Verification
                    java.lang.System.out.println("[POST]:[System]:" + system.getSystemType() + ":[CurrentEnergy]:" + system.getEnergy()
                            + ":[Add-Energy]:" + amount);
                    java.lang.System.out.println("[POST]:[Ship]:" + ship.getId() + ":[Energy]:" + ship.getEnergy());
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
            if (amount<0){
                amount *= -1;
            }
            // Dont trust client data
            ship = shipDAO.getById(ship.getId());
            system = (System) roomDAO.getById(system.getId());
            // Manual verification
            java.lang.System.out.println("\n==================== ACTION REMOVE ENERGY ====================");
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
                            if (((System) r).getEnergy() - amount == 0) {
                                // Disable system if no energy
                                ((System) r).setDisabled(true);
                            }
                            // Heal crew in Medbay
                            if (((System) r).getSystemType().equals(SystemType.MEDBAY)
                                    && !ship.isInCombat() && ((System) r).getEnergy()>0){
                                for (Crew c : r.getCrew()){
                                    c.setHealth(c.getMaxhealth());
                                }
                            }
                            // Set new energy levels
                            ((System) r).setEnergy(((System) r).getEnergy() - amount);
                            ship.setEnergy(ship.getEnergy() + amount);
                            rooms.set(rooms.indexOf(r), ((System) r));
                            java.lang.System.out.println("[System]:[Removed-Energy]");
                            break;
                        }
                    }
                    // Update data
                    for (Room r : ship.getSystems()){
                        roomDAO.update(r);
                    }
                    ship.setSystems(rooms);
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
            // Verification
            java.lang.System.out.println("\n==================== ACTION UPGRADE SYSTEM ====================");
            java.lang.System.out.println("[PRE]:[System]:"+system.getSystemType().toString()+":[Level]:"+system.getMaxEnergy());
            java.lang.System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[Coins]:" + ship.getCoins());
            // Check if the system exists oboard the ship
            boolean systemExists = false;
            for (Room r : ship.getSystems()) {
                if (r.isSystem() && r.getId() == system.getId()) {
                    systemExists = true;
                    system = ((System) r);
                }
            }
            java.lang.System.out.println("[Exists]:"+systemExists);
            if (systemExists) {
                // Compute price and subtract from system coins
                switch (system.getSystemType()) {
                    case COCKPIT:
                        switch (system.getMaxEnergy()) {
                            case 1: // First upgrade
                                if (ship.getCoins() >= 15) {
                                    ship.setCoins(ship.getCoins() - 15);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 2: // Second upgrade
                                if (ship.getCoins() >= 25) {
                                    ship.setCoins(ship.getCoins() - 25);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                            default:
                                return responseObject;
                        }
                    case WEAPON_SYSTEM:
                        switch (system.getMaxEnergy()) {
                            case 1: // First upgrade
                                if (ship.getCoins() >= 50) {
                                    ship.setCoins(ship.getCoins() - 50);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 2: // Second upgrade
                                if (ship.getCoins() >= 25) {
                                    ship.setCoins(ship.getCoins() - 25);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 3: // Third upgrade
                                if (ship.getCoins() >= 40) {
                                    ship.setCoins(ship.getCoins() - 40);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 4: // Fourth upgrade
                                if (ship.getCoins() >= 60) {
                                    ship.setCoins(ship.getCoins() - 60);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 5: // Fifth upgrade
                                if (ship.getCoins() >= 80) {
                                    ship.setCoins(ship.getCoins() - 80);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 6: // Sixth upgrade
                                if (ship.getCoins() >= 90) {
                                    ship.setCoins(ship.getCoins() - 90);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 7: // Final upgrade
                                if (ship.getCoins() >= 100) {
                                    ship.setCoins(ship.getCoins() - 100);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            default:
                                return responseObject;
                        }
                    case CAMERAS:
                        switch (system.getMaxEnergy()) {
                            case 1: // First upgrade
                                if (ship.getCoins() >= 35) {
                                    ship.setCoins(ship.getCoins() - 35);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 2: // Second upgrade
                                if (ship.getCoins() >= 40) {
                                    ship.setCoins(ship.getCoins() - 40);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            default:
                                return responseObject;
                        }
                    case SHIELDS:
                        switch (system.getMaxEnergy()) {
                            case 1: // First upgrade is free
                                system.setMaxEnergy(system.getMaxEnergy() + 1);
                                break;
                            case 2: // Second upgrade
                                if (ship.getCoins() >= 20) {
                                    ship.setCoins(ship.getCoins() - 20);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 3: // Third upgrade
                                if (ship.getCoins() >= 25) {
                                    ship.setCoins(ship.getCoins() - 25);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 4: // Fourth upgrade
                                if (ship.getCoins() >= 30) {
                                    ship.setCoins(ship.getCoins() - 30);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 5: // Fifth upgrade
                                if (ship.getCoins() >= 40) {
                                    ship.setCoins(ship.getCoins() - 40);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 6: // Sixth upgrade
                                if (ship.getCoins() >= 55) {
                                    ship.setCoins(ship.getCoins() - 55);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 7: // Final upgrade
                                if (ship.getCoins() >= 75) {
                                    ship.setCoins(ship.getCoins() - 75);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            default:
                                return responseObject;
                        }
                    case ENGINE:
                        switch (system.getMaxEnergy()) {
                            case 1: // First upgrade
                                if (ship.getCoins() >= 15) {
                                    ship.setCoins(ship.getCoins() - 15);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 2: // Second upgrade
                                if (ship.getCoins() >= 25) {
                                    ship.setCoins(ship.getCoins() - 25);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 3: // Third upgrade
                                if (ship.getCoins() >= 40) {
                                    ship.setCoins(ship.getCoins() - 40);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 4: // Fourth upgrade
                                if (ship.getCoins() >= 60) {
                                    ship.setCoins(ship.getCoins() - 60);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 5: // Fifth upgrade
                                if (ship.getCoins() >= 80) {
                                    ship.setCoins(ship.getCoins() - 80);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 6: // Sixth upgrade
                                if (ship.getCoins() >= 100) {
                                    ship.setCoins(ship.getCoins() - 100);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 7: // Final upgrade
                                if (ship.getCoins() >= 125) {
                                    ship.setCoins(ship.getCoins() - 125);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            default:
                                return responseObject;
                        }
                    case O2:
                        switch (system.getMaxEnergy()) {
                            case 1: // First upgrade
                                if (ship.getCoins() >= 30) {
                                    ship.setCoins(ship.getCoins() - 30);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 2: // Second upgrade
                                if (ship.getCoins() >= 35) {
                                    ship.setCoins(ship.getCoins() - 35);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            default:
                                return responseObject;
                        }
                    case MEDBAY:
                        switch (system.getMaxEnergy()) {
                            case 1: // First upgrade
                                if (ship.getCoins() >= 20) {
                                    ship.setCoins(ship.getCoins() - 20);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            case 2: // Second upgrade
                                if (ship.getCoins() >= 30) {
                                    ship.setCoins(ship.getCoins() - 30);
                                    system.setMaxEnergy(system.getMaxEnergy() + 1);
                                } else {
                                    return responseObject;
                                }
                                break;
                            default:
                                return responseObject;
                        }
                }
                for (Room r : ship.getSystems()){
                    roomDAO.update(r);
                }
                // Update data
                shipDAO.update(ship);
                // Verification
                java.lang.System.out.println("[POST]:[System]:"+system.getSystemType().toString()+":[Level]:"+system.getMaxEnergy());
                java.lang.System.out.println("[POST]:[Ship]:" + ship.getId() + ":[Coins]:" + ship.getCoins());
                java.lang.System.out.println("\n===============================================================");
                // Set valid
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Install a system
     *
     * @param ship       - the client ship
     * @param systemType - the type of system to install
     */
    public ResponseObject installSystem(Ship ship, SystemType systemType) {
        ResponseObject responseObject = new ResponseObject();
        try {
            List<Room> rooms = ship.getSystems();
            for (Room r : rooms) {
                if (r.isSystem() && ((System) r).getSystemType().equals(systemType) && !((System) r).isUnlocked()) {
                    System updated = (System) r;
                    updated.setDisabled(true);
                    updated.setUnlocked(true);
                    rooms.set(rooms.indexOf(r), updated);
                    roomDAO.update(updated);
                    break;
                } else if (r.isSystem() && ((System) r).getSystemType().equals(systemType) && ((System) r).isUnlocked()) {
                    return responseObject;
                }
            }
            for (Room r : ship.getSystems()){
                roomDAO.update(r);
            }
            ship.setSystems(rooms);
            shipDAO.update(ship);
            responseObject.setValidRequest(true);
            responseObject.setResponseShip(ship);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }
}
