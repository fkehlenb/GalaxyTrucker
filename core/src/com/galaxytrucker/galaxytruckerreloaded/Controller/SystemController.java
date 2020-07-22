package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import lombok.*;


/** Manages ship systems */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class SystemController extends Controller {

    /** ClientControllerCummunicator */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /** Remove energy from system
     * @param system - the system to remove energy from
     * @param amount - amount of energy to remove
     * @return valid action */
    public boolean removeEnergy(System system,int amount){
        return false;
    }

    /** Add energy to a system
     * @param system - the system to add energy to
     * @param amount - amount of energy to add
     * @return valid action */
    public boolean addEnergy(System system,int amount){
        return false;
    }

    /** Upgrade a system
     * @param system - the system to upgrade
     * @return valid action */
    public boolean upgradeSystem(System system){
        return false;
    }
}
