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

    /** Response object */
    private ResponseObject previousResponse = null;

    /**
     * Fetch updated data before each round
     *
     * @return is it my round?
     */
    public boolean isMyRound() {
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.ROUND_UPDATE_DATA);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        previousResponse = clientControllerCommunicator.sendRequest(requestObject);
        if (previousResponse.isValidRequest()) {
            clientControllerCommunicator.setClientShip(previousResponse.getResponseShip());
            opponent = previousResponse.getResponseShip();
            return previousResponse.isMyRound();
        }
        return false;
    }

    // After arriving at planet:
    // getPlanetEvent (Non PVP) -> [isMyRound:true->action->isMyRound]
    //                             [isMyRound:false->isCombatOver->isCombatWon->isCombatLost->continue]

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
                return true;
            }
        }
        return false;
    }
}
