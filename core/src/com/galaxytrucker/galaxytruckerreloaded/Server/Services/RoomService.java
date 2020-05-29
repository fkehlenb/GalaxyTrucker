package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import lombok.*;

/** Handles the logic associated with a ships internal rooms and systems */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class RoomService {

    /** ShipDAO */
    private ShipDAO shipDAO;

    /** RoomDAO */
    @NonNull
    private RoomDAO roomDAO;

    /** Cause a breach in a room
     * @param ship - the ship the breach is happening on
     * @param room - the room the breach is happening in */
    public void causeBreach(Ship ship,Room room){}

    /** Disable a system
     * @param ship - the ship to disable a system on
     * @param system - the system to disable */
    public void disableSystem(Ship ship, System system){

    }

    /** Re-enable a system
     * @param ship - the ship to re-enable a system on
     * @param system - the system to re-enable */
    public void reEnableSystem(Ship ship,System system){

    }
}
