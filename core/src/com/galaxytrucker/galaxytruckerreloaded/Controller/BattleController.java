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

    private static BattleController battleController;

    public static BattleController getInstance(ClientControllerCommunicator clientControllerCommunicator){
        if (battleController == null){
            battleController = new BattleController(clientControllerCommunicator);
        }
        return battleController;
    }
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

    // Todo: "if at pvp planet, remove hyperjump button and replace with different one
    // todo: that returns user to previous/destination planet"
    // After arriving at planet:
    // getPlanetEvent -> [isMyRound:true->action->isMyRound]
    //                   [isMyRound:false->isCombatOver->isCombatWon->isCombatLost->continue]

    /** Check if the combat is over
     * @return combat over
     * @throws NullPointerException if the previous response object is null */
    public boolean isCombatOver() throws NullPointerException{
        return previousResponse.isCombatOver();
    }

    /** Check if the combat was won
     * @return combat won
     * @throws NullPointerException if the previous response object is null */
    public boolean isCombatWon() throws NullPointerException{
        return previousResponse.isCombatWon();
    }

    /** Check if combat was lost
     * @return combat lost
     * @throws NullPointerException if the previous response object is null */
    public boolean isCombatLost() throws NullPointerException{
        return previousResponse.isDead();
    }

    /**
     * Attack opponent ship
     *
     * @param weapon - the weapon to attack with
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
            if (responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                opponent = responseObject.getOpponent();
                return true;
            }
        return false;
    }
}
