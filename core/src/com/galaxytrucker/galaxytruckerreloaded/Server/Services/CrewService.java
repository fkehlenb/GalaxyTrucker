package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

import java.util.List;

/** This class handles the logic for crew aboard the ship, server side */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CrewService {

    // TODO *** DO NOT USE WHEN IN COMBAT, USE THE DESIGNATED COMBAT AND PVP CONTROLLERS! ***

    /** ShipDAO */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /** CrewDAO */
    private CrewDAO crewDAO = CrewDAO.getInstance();

    /** RoomDAO */
    private RoomDAO roomDAO = RoomDAO.getInstance();

    /** Move a crew member to a different section
     * @param ship - the ship the crew is on
     * @param crew - the crew member
     * @param room - the room to move him to */
    @SuppressWarnings("Duplicates")
    public ResponseObject moveCrewToRoom(Ship ship, Crew crew, Room room){
        ResponseObject responseObject = new ResponseObject();
        try {
            // No trusting client data
            ship = shipDAO.getById(ship.getId());
            crew = crewDAO.getById(crew.getId());
            room = roomDAO.getById(room.getId());
            // Manual verification
            java.lang.System.out.println("\n==================== ACTION MOVE CREW ====================");
            java.lang.System.out.println("[PRE]:[SHIP]:" + ship.getId() + ":[CREW]:" + crew.getId() + ":[ROOM]:" + crew.getCurrentRoom().getId());
            // Check if the crew exists in the ship
            boolean existsInShip = false;
            for (Room r : ship.getSystems()){
                if (r.getCrew().contains(crew) && r.getTiles().size()>r.getCrew().size()){
                    existsInShip = true;
                    java.lang.System.out.println("[CREW-FOUND]:[ENOUGH-ROOM-REMAINING]");
                }
            }
            // Crew must not move to same room it is already in
            if (existsInShip && room.getId()!=crew.getCurrentRoom().getId()&&!ship.isInCombat()){
                Room currentRoom = crew.getCurrentRoom();
                List<Crew> crewInRoom = currentRoom.getCrew();
                List<Tile> roomTiles = currentRoom.getTiles();
                // Remove crew from tile
                for (Tile t : roomTiles){
                    if (t.getStandingOnMe().getId()==crew.getId()){
                        t.setStandingOnMe(null);
                        roomTiles.set(roomTiles.indexOf(t), t);
                    }
                }
                // Remove crew from room
                currentRoom.setTiles(roomTiles);
                crewInRoom.remove(crew);
                currentRoom.setCrew(crewInRoom);
                // Add crew to tile in new room
                roomTiles = room.getTiles();
                for (Tile t : roomTiles){
                    if (t.isEmpty()){
                        t.setStandingOnMe(crew);
                        roomTiles.set(roomTiles.indexOf(t),t);
                    }
                }
                room.setTiles(roomTiles);
                // Add crew to room
                crewInRoom = room.getCrew();
                crewInRoom.add(crew);
                room.setCrew(crewInRoom);
                // Change crew room
                crew.setCurrentRoom(room);
                // Update ship rooms
                List<Room> rooms = ship.getSystems();
                for (Room r : rooms){
                    if (r.getId()==currentRoom.getId()){
                        rooms.set(rooms.indexOf(r),currentRoom);
                    }
                    else if (r.getId()==room.getId()){
                        rooms.set(rooms.indexOf(r),room);
                    }
                }
                ship.setSystems(rooms);
                // Update data
                roomDAO.update(currentRoom);
                roomDAO.update(room);
                crewDAO.update(crew);
                shipDAO.update(ship);
                // Manual verification
                java.lang.System.out.println("[POST]:[SHIP]:" + ship.getId() + ":[CREW]:" + crew.getId() + ":[ROOM]:" + crew.getCurrentRoom().getId());
                java.lang.System.out.println("==========================================================");
                // Set valid data
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    /** Heal crew
     * @param ship - the ship the crew is on
     * @param crew - the crew member to heal
     * @param healAmount - amount to heal */
    public void healCrewMember(Ship ship,Crew crew,int healAmount){

    }

    /** Heal crew in a room
     * @param ship - the ship the crew are on
     * @param room - the room which's crew members to heal
     * @param amount - amount to heal */
    public void healCrewInRoom(Ship ship,Room room,int amount){

    }

    /** Damage crew
     * @param ship - the ship the crew is on
     * @param room - the room in which to damage the crew
     * @param amount - the amount of damage to take */
    public void damageCrew(Ship ship,Room room,int amount){

    }

    /** Fix a system
     * @param ship - the ship to fix a system on
     * @param system - the system to fix */
    public void fixSystem(Ship ship, System system){

    }

    /** Repair a breach in a room
     * @param ship - the ship to fix the rbeach on
     * @param room - the room to fix the breach in */
    public void repairBreach(Ship ship,Room room){

    }
}
