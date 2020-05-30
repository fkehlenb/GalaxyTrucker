package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CrewController extends Controller{
    @NonNull
    private Ship myself;

    /** Move a crew member to a different section
     * @param crew - the crew member
     * @param room - the room to move him to */
    public void moveCrewToRoom(Crew crew, Room room){

    }

    /** Heal crew
     * @param crew - the crew member to heal
     * @param healAmount - amount to heal */
    public void healCrewMember(Crew crew,int healAmount){

    }

    /** Heal crew in a room
     * @param room - the room which's crew members to heal
     * @param amount - amount to heal */
    public void healCrewInRoom(Room room,int amount){

    }

    /** Damage crew
     * @param room - the room in which to damage the crew
     * @param amount - the amount of damage to take */
    public void damageCrew(Room room,int amount){

    }

    /** Fix a system
     * @param system - the system to fix */
    public void fixSystem(System system){

    }

    /** Repair a breach in a room
     * @param room - the room to fix the breach in */
    public void repairBreach(Room room){

    }
}
