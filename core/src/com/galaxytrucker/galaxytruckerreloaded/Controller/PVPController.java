package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/** Sends PVP Request */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PVPController {

    /** Client controller communicator */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /** List of all connected clients */
    private List<String> pvpClients = new ArrayList<>();

    /** Instance */
    private static PVPController instance;

    /** Get instance */
    public static PVPController getInstance(ClientControllerCommunicator clientControllerCommunicator){
        if (instance == null){
            instance = new PVPController(clientControllerCommunicator);
        }
        return instance;
    }

    /** Get a list of connected clients from the server
     * @return a list of all connected hosts */
    public List<String> getPvpOpponents(){
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.PVP_GET_CLIENTS);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            this.pvpClients = responseObject.getPvpClients();
        }
        return this.pvpClients;
    }

    /** Send a new pvp request
     * @param name - the player to attack
     * @return true if the request is valid */
    public boolean sendPVPRequest(String name){
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.INITIATE_PVP);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        requestObject.setPvpOpponent(name);
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            BattleController.getInstance(null).setOpponent(responseObject.getOpponent());
            return true;
        }
        return false;
    }

    /** Set yourself as a pvp client
     * @param ship - your ship */
    public boolean activatePVP(Ship ship){
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.ACTIVATE_PVP);
        requestObject.setShip(ship);
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            return true;
        }
        return false;
    }
}
