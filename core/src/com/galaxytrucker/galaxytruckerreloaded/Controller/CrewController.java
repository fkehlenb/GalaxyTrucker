package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CrewController extends Controller {

    private static CrewController singleton;

    /**
     * ClientControllerCommunicator
     */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /**
     * return the instance of this singleton
     * @param communicator the communicator
     * @return the singleton instance
     */
    public static CrewController getInstance(ClientControllerCommunicator communicator) {
        if(singleton == null) {
            singleton = new CrewController(communicator);
        }
        return singleton;
    }

    /**
     * Move a crew member to a different section
     *
     * @param crew - the crew member
     * @param room - the room to move him to
     */
    public boolean moveCrewToRoom(Crew crew, Room room) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.MoveCrew);
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setCrew(crew);
            requestObject.setRoom(room);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if (responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Heal crew
     *
     * @param crew       - the crew member to heal
     * @param healAmount - amount to heal
     */
    public boolean healCrewMember(Crew crew, int healAmount) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.HealCrew);
            requestObject.setCrew(crew);
            requestObject.setHealAmount(healAmount);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if (responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Heal crew in a room
     *
     * @param room   - the room which's crew members to heal
     * @param amount - amount to heal
     */
    public boolean healCrewInRoom(Room room, int amount) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.HealCrewInRoom);
            requestObject.setRoom(room);
            requestObject.setHealAmount(amount);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if (responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Damage crew
     *
     * @param room   - the room in which to damage the crew
     * @param amount - the amount of damage to take
     */
    public boolean damageCrew(Room room, int amount) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.DamageCrew);
            requestObject.setDamageAmount(amount);
            requestObject.setRoom(room);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if (responseObject.isValidRequest()){
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fix a system
     *
     * @param system - the system to fix
     */
    public void fixSystem(System system) {

    }

    /**
     * Repair a breach in a room
     *
     * @param room - the room to fix the breach in
     */
    public void repairBreach(Room room) {

    }
}
