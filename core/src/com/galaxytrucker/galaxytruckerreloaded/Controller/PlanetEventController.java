package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/** Used when arriving at a new planet */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PlanetEventController {

    /** ClientControllerCommunicator */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /** Instance */
    private static PlanetEventController instance;

    /** Get instance
     * @param clientControllerCommunicator - the communicator */
    public static PlanetEventController getInstance(ClientControllerCommunicator clientControllerCommunicator){
        if (instance == null){
            instance = new PlanetEventController(clientControllerCommunicator);
        }
        return instance;
    }

    /** Fetch the current planet event
     * @return the current planet event */
    public PlanetEvent getPlanetEvent() throws NullPointerException {
        // Todo check for combat/pvp/boss and miniboss in UI
        // if one of these, call battlecontroller isMyTurn
        return clientControllerCommunicator.getClientShip().getPlanet().getEvent();
    }

    /**
     * loads the current players ship
     * @return the players ship
     */
    public Ship getClientShip(){
        return clientControllerCommunicator.getClientShip();
    }
}
