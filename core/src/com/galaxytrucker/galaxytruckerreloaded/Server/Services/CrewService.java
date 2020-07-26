package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.TileDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the logic for crew aboard the ship, server side
 */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@SuppressWarnings("Duplicates")
public class CrewService implements Serializable {

    // TODO *** DO NOT USE WHEN IN COMBAT, USE THE DESIGNATED COMBAT AND PVP CONTROLLERS! ***

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * CrewDAO
     */
    private CrewDAO crewDAO = CrewDAO.getInstance();

    /**
     * RoomDAO
     */
    private RoomDAO roomDAO = RoomDAO.getInstance();

    /**
     * Tile dao
     */
    private TileDAO tileDAO = TileDAO.getInstance();

    /**
     * Instance
     */
    private static CrewService instance;

    /**
     * Get instance
     */
    public static CrewService getInstance() {
        if (instance == null) {
            instance = new CrewService();
        }
        return instance;
    }

    /**
     * Move a crew member to a different section
     *
     * @param ship - the ship the crew is on
     * @param crew - the crew member
     * @param room - the room to move him to
     */
    @SuppressWarnings("Duplicates")
    public ResponseObject moveCrewToRoom(Ship ship, Crew crew, Room room) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // No trusting client data
            ship = shipDAO.getById(ship.getId());
            crew = crewDAO.getById(crew.getId());
            room = roomDAO.getById(room.getId());
            // Manual verification
            java.lang.System.out.println("\n==================== ACTION MOVE CREW ====================");
            java.lang.System.out.println("[PRE]:[SHIP]:" + ship.getId() + ":[CREW]:" + crew.getId() + ":[ROOM]:"
                    + crew.getCurrentRoom().getId());
            // Check if the crew exists in the ship
            boolean existsInShip = false;
            for (Room r : ship.getSystems()) {
                if (r.getCrew().contains(crew)) {
                    existsInShip = true;
                }
            }
            // Check for enough space in taget room
            boolean enoughRoom = false;
            for (Room r : ship.getSystems()) {
                if (r.getId() == room.getId()) {
                    if (r.getTiles().size() > r.getCrew().size()) {
                        enoughRoom = true;
                    }
                }
            }
            // Crew must not move to same room it is already in
            if (existsInShip && room.getId() != crew.getCurrentRoom().getId() && enoughRoom) {
                // Remove crew from current room
                for (Room r : ship.getSystems()) {
                    if (r.getId() == crew.getCurrentRoom().getId()) {
                        List<Crew> crewInRoom = r.getCrew();
                        for (Tile t : r.getTiles()) {
                            if (t.getStandingOnMe() != null) {
                                if (t.getStandingOnMe().getId() == crew.getId()) {
                                    crew = t.getStandingOnMe();
                                    t.getStandingOnMe().setTile(null);
                                    t.setStandingOnMe(null);
                                    break;
                                }
                            }
                        }
                        crewInRoom.remove(crew);
                        r.setCrew(crewInRoom);
                        break;
                    }
                }
                // Add crew to target room
                for (Room r : ship.getSystems()) {
                    if (r.getId() == room.getId()) {
                        List<Crew> crewInRoom = r.getCrew();
                        for (Tile t : r.getTiles()) {
                            if (t.isEmpty()) {
                                t.setStandingOnMe(crew);
                                crew.setTile(t);
                                crew.setCurrentRoom(r);
                                break;
                            }
                        }
                        crewInRoom.add(crew);
                        r.setCrew(crewInRoom);
                        break;
                    }
                }
                if (crew.getCurrentRoom().isSystem() && ((System) crew.getCurrentRoom()).getSystemType().equals(SystemType.MEDBAY)
                        && !ship.isInCombat() && ((System) crew.getCurrentRoom()).getEnergy() > 0) {
                    for (Crew c : crew.getCurrentRoom().getCrew()) {
                        c.setHealth(c.getMaxhealth());
                    }
                }
                for (Room r : ship.getSystems()) {
                    for (Tile t : r.getTiles()) {
                        tileDAO.update(t);
                    }
                    for (Crew c : r.getCrew()) {
                        crewDAO.update(c);
                    }
                    roomDAO.update(r);
                }
                shipDAO.update(ship);
                // Manual verification
                java.lang.System.out.println("[POST]:[SHIP]:" + ship.getId() + ":[CREW]:" + crew.getId() + ":[ROOM]:"
                        + crew.getCurrentRoom().getId());
                java.lang.System.out.println("==========================================================");
                // Set valid data
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }
}
