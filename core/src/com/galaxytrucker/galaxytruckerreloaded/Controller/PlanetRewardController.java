package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/** Handles reward requests */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PlanetRewardController {

    /** Client controller communicator */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /** Rockets reward */
    @Getter
    private int rocketReward = 0;

    /** Fuel reward */
    @Getter
    private int fuelReward = 0;

    /** Money reward */
    @Getter
    private int moneyReward = 0;

    /** Weapon reward */
    @Getter
    private List<Weapon> weaponRewards = new ArrayList<>();

    /** Crew reward */
    @Getter
    private Crew crewReward = null;

    /** Instance */
    private static PlanetRewardController instance;

    /** Get instance
     * @param clientControllerCommunicator - client controller communicator  */
    public static PlanetRewardController getInstance(ClientControllerCommunicator clientControllerCommunicator){
        if (instance == null){
            instance = new PlanetRewardController(clientControllerCommunicator);
        }
        return instance;
    }

    /** Get rewards for current planet */
    public boolean getRewards(){
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.GET_PLANET_REWARDS);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        requestObject.setPlanet(clientControllerCommunicator.getClientShip().getPlanet());
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            this.fuelReward = responseObject.getRewardFuel();
            this.moneyReward = responseObject.getRewardCash();
            this.rocketReward = responseObject.getRewardRockets();
            this.weaponRewards = responseObject.getRewardWeapons();
            if (responseObject.getRewardCrew()!=null){
                this.crewReward = responseObject.getRewardCrew();
            }
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            return true;
        }
        return false;
    }
}
