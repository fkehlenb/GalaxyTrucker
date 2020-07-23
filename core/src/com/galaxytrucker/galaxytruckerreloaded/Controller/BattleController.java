package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BattleController extends Controller {

    /**
     * Opponent ship
     */
    private Ship opponent;

    /** Instance */
    private static BattleController battleController;

    /** Get instance */
    public static BattleController getInstance(ClientControllerCommunicator clientControllerCommunicator) {
        if (battleController == null) {
            battleController = new BattleController(clientControllerCommunicator);
        }
        return battleController;
    }

    /**
     * ClientControllerCommunicator
     */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /**
     * Response object
     */
    private ResponseObject previousResponse = null;

    /**
     * Play your round's moves
     */
    public boolean playMoves() {
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.ROUND_OVER);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()) {
            clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            return true;
        }
        return false;
    }

    /**
     * Fetch updated data
     */
    public boolean fetchUpdatedData() {
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.ROUND_UPDATE_DATA);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()) {
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
            previousResponse = responseObject;
            return true;
        }
        return false;
    }


    /**
     * Attack opponent
     *
     * @param weapon - the weapon to use
     * @param room   - the room to attack
     */
    public boolean attack(Weapon weapon, Room room) {
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.ATTACK_SHIP);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        requestObject.setOpponentShip(opponent);
        requestObject.setWeapon(weapon);
        requestObject.setRoom(room);
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        return responseObject.isValidRequest();
    }
}
