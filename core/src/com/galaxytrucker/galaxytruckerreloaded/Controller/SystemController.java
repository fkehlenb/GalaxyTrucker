package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;


/** Manages ship systems */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@SuppressWarnings("Duplicates")
public class SystemController extends Controller {

    /** ClientControllerCummunicator */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /** Instance */
    private static SystemController instance;

    /** Get instance
     * @param clientControllerCommunicator - the client controller communicator */
    public static SystemController getInstance(ClientControllerCommunicator clientControllerCommunicator){
        if (instance == null){
            instance = new SystemController(clientControllerCommunicator);
        }
        return instance;
    }

    /** Remove energy from system
     * @param system - the system to remove energy from
     * @param amount - amount of energy to remove
     * @return valid action */
    public boolean removeEnergy(System system,int amount){
        RequestObject requestObject = new RequestObject();
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        requestObject.setSystem(system);
        requestObject.setIntAmount(amount);
        requestObject.setRequestType(RequestType.REMOVE_ENERGY_SYSTEM);
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            return true;
        }
        return false;
    }

    /** Add energy to a system
     * @param system - the system to add energy to
     * @param amount - amount of energy to add
     * @return valid action */
    public boolean addEnergy(System system,int amount){
        RequestObject requestObject = new RequestObject();
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        requestObject.setSystem(system);
        requestObject.setIntAmount(amount);
        requestObject.setRequestType(RequestType.ADD_ENERGY_SYSTEM);
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            return true;
        }
        return false;
    }

    /** Upgrade a system
     * @param system - the system to upgrade
     * @return valid action */
    public boolean upgradeSystem(System system){
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.UPGRADE_SYSTEM);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        requestObject.setSystem(system);
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            return true;
        }
        return false;
    }

    /** Install a new system
     * @param systemType - the type of system to install
     * @return valid action */
    public boolean installSystem(SystemType systemType){
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.INSTALL_SYSTEM);
        requestObject.setSystemType(systemType);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            return true;
        }
        return false;
    }
}
