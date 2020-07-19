package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import lombok.*;

/** This class handles the logic for crew aboard the ship, server side */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CrewService {

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
    public void moveCrewToRoom(Ship ship, Crew crew, Room room){

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
