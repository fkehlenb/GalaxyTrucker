package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import com.galaxytrucker.galaxytruckerreloaded.Server.PreviousRoundAction;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    /** Previous round actions */
    private List<PreviousRoundAction> previousRoundActions = new ArrayList<>();

    /** Previous weapons used */
    private List<WeaponType> previousWeaponsUsed = new ArrayList<>();

    /** Check if the combat is over
     * @return combat over */
    public boolean combatOver(){
        if (previousResponse!=null){
            return previousResponse.isCombatOver();
        }
        return false;
    }

    /** Check if combat is won
     * @return combat won */
    public boolean combatWon(){
        if (previousResponse!=null){
            return previousResponse.isCombatWon();
        }
        return false;
    }

    /** Get the opponent's previous round actions
     * @return previous round actions */
    public List<PreviousRoundAction> getPreviousRoundActions(){
        if (!previousRoundActions.isEmpty()){
            return previousRoundActions;
        }
        return new ArrayList<>();
    }

    /** Get the weapons used by the opponent
     * @return previous round weapons */
    public List<WeaponType> getPreviousRoundWeapons(){
        if (!previousWeaponsUsed.isEmpty()){
            return previousWeaponsUsed;
        }
        return new ArrayList<>();
    }

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
            if (responseObject.getPreviousRoundAction()!=null&&!responseObject.getPreviousRoundAction().isEmpty()){
                previousRoundActions = responseObject.getPreviousRoundAction();
            }
            else{
                previousRoundActions = new ArrayList<>();
            }
            if (responseObject.getWeaponUsed()!=null&&!responseObject.getWeaponUsed().isEmpty()){
                previousWeaponsUsed = responseObject.getWeaponUsed();
            }
            else{
                previousWeaponsUsed = new ArrayList<>();
            }
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
