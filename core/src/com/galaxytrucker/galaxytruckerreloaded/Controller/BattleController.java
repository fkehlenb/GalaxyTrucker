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
    @NonNull
    private Ship opponent;

    /**
     * ClientControllerCommunicator
     */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /**
     * Fetch updated data before each round
     *
     * @return is it my round?
     */
    public boolean isMyRound() {
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.ROUND_UPDATE_DATA);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
        opponent = responseObject.getResponseShip();
        return responseObject.isMyRound();
    }

    /**
     * Attack opponent ship
     *
     * @param weapon - the weapon to attack with
     * @param room   - the room to attack
     */
    public boolean attack(Weapon weapon, Room room) {
        if (isMyRound()) {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.ATTACK_SHIP);
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setOpponentShip(opponent);
            requestObject.setWeapon(weapon);
            requestObject.setRoom(room);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if (responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                opponent = responseObject.getOpponent();
                // TODO add fight won, return false
                return true;
            }
        }
        return false;
    }
}
