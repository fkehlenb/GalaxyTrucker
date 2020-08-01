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
                    break;
                }
            }
            // Check for enough space in taget room
            boolean enoughRoom = false;
            for (Room r : ship.getSystems()) {
                if (r.getId() == room.getId()) {
                    if (r.getTiles().size() > r.getCrew().size()) {
                        enoughRoom = true;
                        break;
                    }
                }
            }
            // Crew must not move to same room it is already in
            if (existsInShip && room.getId() != crew.getCurrentRoom().getId() && enoughRoom) {
                // Remove crew from current room
                for (Room r : ship.getSystems()) {
                    if (r.getId() == crew.getCurrentRoom().getId()) {
                        for (Tile t : r.getTiles()) {
                            if (t.getStandingOnMe() != null) {
                                if (t.getStandingOnMe().getId() == crew.getId()) {
                                    t.setStandingOnMe(null);
                                    tileDAO.update(t);
                                    crew.setTile(null);
                                    crew.setCurrentRoom(null);
                                    crewDAO.update(crew);
                                    break;
                                }
                            }
                        }
                        r = roomDAO.getById(r.getId());
                        List<Crew> crewInRoom = r.getCrew();
                        for (Crew c : crewInRoom){
                            if (c.getId() == crew.getId()){
                                crewInRoom.remove(c);
                                break;
                            }
                        }
                        r.setCrew(crewInRoom);
                        roomDAO.update(r);
                        break;
                    }
                }
                // Add crew to target room
                for (Room r : ship.getSystems()) {
                    if (r.getId() == room.getId()) {
                        for (Tile t : r.getTiles()) {
                            if (t.isEmpty()) {
                                t.setStandingOnMe(crew);
                                tileDAO.update(t);
                                crew.setTile(t);
                                crew.setCurrentRoom(r);
                                crewDAO.update(crew);
                                break;
                            }
                        }
                        r = roomDAO.getById(r.getId());
                        List<Crew> crewInRoom = r.getCrew();
                        crewInRoom.add(crew);
                        r.setCrew(crewInRoom);
                        roomDAO.update(r);
                        break;
                    }
                }
                if (crew.getCurrentRoom().isSystem() && ((System) crew.getCurrentRoom()).getSystemType().equals(SystemType.MEDBAY)
                        && !ship.isInCombat() && ((System) crew.getCurrentRoom()).getEnergy() > 0) {
                    for (Crew c : crew.getCurrentRoom().getCrew()) {
                        c.setHealth(c.getMaxhealth());
                        crewDAO.update(c);
                    }
                }
                ship = shipDAO.getById(ship.getId());
                // Manual verification
                java.lang.System.out.println("[POST]:[SHIP]:" + ship.getId() + ":[CREW]:" + crew.getId() + ":[ROOM]:"
                        + crew.getCurrentRoom().getId());
                java.lang.System.out.println("==========================================================");
                // Set valid data
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return responseObject;
    }
}
