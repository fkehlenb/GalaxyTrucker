package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class SystemController extends Controller {

    /** My own ship*/
    private Ship myself;

    /**
     * Install a new system on a ship
     *
     * @param system - the system to install
     * @param room   - the room to install the system in
     */
    public void installSystem(System system, Room room) {

    }

    /**
     * Uninstall a system on a ship
     *
     * @param system - the system to remove
     */
    public void uninstalledSystem(System system) {

    }

}
