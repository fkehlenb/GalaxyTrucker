package com.galaxytrucker.galaxytruckerreloaded.Controller;


import com.badlogic.gdx.Screen;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.CrewStat;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.UserService;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Duplicates")
public class TraderController extends Controller{

    /** ClientControllerCommunicator */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /** Instance */
    private static TraderController singleton;

    /** Get instance */
    public static TraderController getInstance(ClientControllerCommunicator communicator) {
        if(singleton == null) {
            singleton = new TraderController(communicator);
        }
        return singleton;
    }

    /**
     * Buy a weapon from the trader
     * @param weapon - the weapon to buy
     */
    public boolean purchaseWeapon(Trader trader, Weapon weapon) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setWeapon(weapon);
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setTrader(trader);
            requestObject.setRequestType(RequestType.TRADERBUYWEAPON);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if (responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Buy crew from a trader
     * @param crew   - the crew to buy
     */
    public boolean purchaseCrew(Trader trader, Crew crew) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.TRADERBUYCREW);
            requestObject.setTrader(trader);
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setCrew(crew);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if(responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Buy rockets from the trader
     * @param amount - the amount of rockets to buy
     */
    public boolean purchaseRockets(Trader trader, int amount) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.TRADERBUYROCKETS);
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setTrader(trader);
            requestObject.setIntAmount(amount);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if(responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Buy fuel from the trader
     * @param amount - the amount of fuel to buy
     */
    public boolean purchaseFuel(Trader trader, int amount) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setTrader(trader);
            requestObject.setRequestType(RequestType.TRADERBUYFUEL);
            requestObject.setIntAmount(amount);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if(responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Buy health from the trader
     * @param amount - the amount to buy
     */
    public boolean purchaseHP(Trader trader, int amount) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setTrader(trader);
            requestObject.setRequestType(RequestType.TRADERBUYHP);
            requestObject.setIntAmount(amount);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if(responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sell weapon to the trader
     * @param weapon the weapon
     */
    public boolean sellWeapon(Trader trader, Weapon weapon) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setTrader(trader);
            requestObject.setWeapon(weapon);
            requestObject.setRequestType(RequestType.TRADERSELLWEAPON);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if(responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * sell rockets to the trader
     * @param amount the amount of rockets
     */
    public boolean sellRockets(Trader trader, int amount) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setTrader(trader);
            requestObject.setRequestType(RequestType.TRADERSELLROCKETS);
            requestObject.setIntAmount(amount);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if(responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                clientControllerCommunicator.setMap(responseObject.getResponseOverworld());
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Upgrade crew
     * @param crew - the crew to upgrade
     * @param stat - the stat to upgrade */
    public boolean upgradeCrew(Crew crew, CrewStat stat){
        RequestObject requestObject = new RequestObject();
        requestObject.setRequestType(RequestType.UPGRADE_CREW);
        requestObject.setShip(clientControllerCommunicator.getClientShip());
        requestObject.setCrew(crew);
        requestObject.setStat(stat);
        ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
        if (responseObject.isValidRequest()){
            clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
            return true;
        }
        return false;
    }

    /**
     * upgrade crew, random stat
     * @param crew the crew member
     * @return whether it was successful
     */
    public boolean upgradeCrew(Crew crew) {
        Random random = new Random();
        List<CrewStat> stats = new ArrayList<>();
        stats.add(CrewStat.ENGINE);
        stats.add(CrewStat.COMBAT);
        stats.add(CrewStat.REAPAIR);
        stats.add(CrewStat.SHIELD);
        stats.add(CrewStat.WEAPON);
        return upgradeCrew(crew, stats.get(random.nextInt(5)));
    }
}
