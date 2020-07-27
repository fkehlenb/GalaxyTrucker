package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

/** Handles weapons on board */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class WeaponController extends Controller {

    /**
     * ClientControllerCommunicator
     */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    private static WeaponController singleton;

    public static WeaponController getInstance(ClientControllerCommunicator clientControllerCommunicator) {
        if(singleton == null) {
            singleton = new WeaponController(clientControllerCommunicator);
        }
        return singleton;
    }

    /** Equip a weapon
     * @param w - the weapon to equip
     * @return valid request */
    public boolean equipWeapon(Weapon w){
        try {
            Ship myShip = clientControllerCommunicator.getClientShip();
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.EQUIP_WEAPON);
            requestObject.setShip(myShip);
            requestObject.setWeapon(w);
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

    /** Unequip weapon
     * @param w - the weapon to unequip
     * @return is valid request */
    public boolean unequipWeapon(Weapon w){
        try {
            Ship myShip = clientControllerCommunicator.getClientShip();
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.UNEQIP_WEAPON);
            requestObject.setShip(myShip);
            requestObject.setWeapon(w);
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

}
