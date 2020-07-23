package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.BattleServiceDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RequestObjectDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** ServerServiceCommunicator for executing logic using services, singleton */
@Getter
@Setter
public class ServerServiceCommunicator {

    /** ServerServiceCommunicator */
    private static ServerServiceCommunicator serverServiceCommunicator = null;

    /** List of battle services */
    private List<BattleService> battleServices;

    /** Battle service dao */
    private BattleServiceDAO battleServiceDAO = BattleServiceDAO.getInstance();

    /** Request Object DAO */
    private RequestObjectDAO requestObjectDAO = RequestObjectDAO.getInstance();

    /** User service */
    private UserService userService = new UserService();

    /** TravelService */
    private TravelService travelService = new TravelService();

    /** TraderService */
    private TraderService traderService = new TraderService();

    /** Weapon service */
    private WeaponService weaponService = new WeaponService();

    /** System Service */
    private SystemService systemService = new SystemService();

    /** Crew Service */
    private CrewService crewService = new CrewService();

    /** Take a request from the client side, pass it through the services
     * and return a response
     * @return the server's response to the client's request */
    public ResponseObject getResponse(RequestObject request){
        try {
            request.setId(UUID.randomUUID().hashCode());
            requestObjectDAO.persist(request);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        switch (request.getRequestType()){
            case MoveCrew:
                // TODO combat
                return crewService.moveCrewToRoom(request.getShip(),request.getCrew(),request.getRoom());
            case HealCrew:
                // TODO combat
                return crewService.healCrewMember(request.getShip(),request.getCrew(),request.getHealAmount());
            case LOGOUT:
                // TODO combat
                return logout(request.getShip().getAssociatedUser());
            case ADD_ENERGY_SYSTEM:
                return systemService.addEnergy(request.getShip(),request.getSystem(),request.getIntAmount());
            case REMOVE_ENERGY_SYSTEM:
                return systemService.removeEnergy(request.getShip(),request.getSystem(),request.getIntAmount());
            case UPGRADE_SYSTEM:
                return systemService.upgradeSystem(request.getShip(),request.getSystem());
            case HYPERJUMP:
                if (request.getShip().isInCombat()&&!request.isPvp()){
                    for (BattleService b : battleServices){
                        if (b.getPlayerOne().getId()==request.getShip().getId()
                                || b.getPlayerTwo().getId()==request.getShip().getId()){
                            return b.fleeFight(request.getShip(),request.getPlanet());
                        }
                    }
                }
                else if (request.getShip().isInCombat()&&request.isPvp()){
                    // Todo pvp service
                }
                return travelService.jump(request.getShip(),request.getPlanet());
            case ROUND_UPDATE_DATA:
                if (request.getShip().isInCombat()){
                    if (!request.isPvp()){
                        for (BattleService b : battleServices){
                            if (b.getPlayerOne().getId()==request.getShip().getId()
                                    || b.getPlayerTwo().getId()==request.getShip().getId()){
                                return b.getUpdatedData(request.getShip());
                            }
                        }
                    }
                    else{
                        // Todo pvp
                    }
                }
            case ATTACK_SHIP:
                if (request.getShip().isInCombat()){
                    if (!request.isPvp()){
                        for (BattleService b : battleServices){
                            if (b.getPlayerOne().getId()==request.getShip().getId()
                                    || b.getPlayerTwo().getId()==request.getShip().getId()){
                                return b.attackShip(request.getShip(),request.getWeapon(),
                                        request.getOpponentShip(),request.getRoom());
                            }
                        }
                    }
                    else{
                        // todo pvp
                    }
                }
            case TRADERBUYCREW:
                return traderService.purchaseCrew(request.getShip(), request.getTrader(), request.getCrew());
            case TRADERBUYFUEL:
                return traderService.purchaseFuel(request.getShip(), request.getTrader(), request.getIntAmount());
            case TRADERBUYWEAPON:
                return traderService.purchaseWeapon(request.getShip(), request.getTrader(), request.getWeapon());
            case TRADERBUYHP:
                return traderService.purchaseHP(request.getShip(), request.getTrader(), request.getIntAmount());
            case TRADERBUYROCKETS:
                return traderService.purchaseRockets(request.getShip(), request.getTrader(), request.getIntAmount());
            case TRADERSELLROCKETS:
                return traderService.sellRockets(request.getShip(), request.getTrader(), request.getIntAmount());
            case TRADERSELLWEAPON:
                return traderService.sellWeapon(request.getShip(), request.getTrader(), request.getWeapon());
            case EQUIP_WEAPON:
                return weaponService.equipWeapon(request.getShip(),request.getWeapon());
            case UNEQIP_WEAPON:
                return weaponService.unequipWeapon(request.getShip(),request.getWeapon());
        }
        // Returning null is VORBIDDEN!
        ResponseObject defaultResponse = new ResponseObject();
        defaultResponse.setValidRequest(false);
        return defaultResponse;
    }

    // ==================================== USER SERVICE ====================================

    /** Send the client his ship
     * @param username - the client's username
     * @return the client's ship */
    public Ship getClientShip(String username) throws UserNotFoundException {
        return userService.getUser(username).getUserShip();
    }

    /** Send the client his overWorld map
     * @param username - the client's username
     * @return the client's overWorld map
     * @throws UserNotFoundException if the user cannot be found */
    public Overworld getClientMap(String username) throws UserNotFoundException {
        return userService.getUser(username).getOverworld();
    }

    /** Login
     * @param username - the user that wants to login
     * @return if the client is allowed to login */
    public boolean isLoggedIn(String username) throws IllegalArgumentException {
        try {
            return userService.getUser(username).isLoggedIn();
        }
        catch (Exception e){
            System.out.println("[NEW-USER]:[USERNAME]:"+username);
            try {
                if (username.isEmpty() || username.equals("[ENEMY]")){
                    return true;
                }
                userService.addUser(username);
                return false;
            }
            catch (Exception f){
                f.printStackTrace();
                throw new IllegalArgumentException();
            }
        }
    }

    /** Logout
     * @param username - the client's username
     * @return ResponseObject with either an accepted or aborted logout */
    private ResponseObject logout(String username){
        ResponseObject responseObject = new ResponseObject();
        try {
            User u = userService.getUser(username);
            if (u.isLoggedIn()) {
                if (!u.getUserShip().isInCombat()) {
                    u.setLoggedIn(false);
                    userService.updateUser(u);
                    responseObject.setValidRequest(true);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            responseObject.setValidRequest(false);
        }
        return responseObject;
    }

    /** Log user out after exception in client handler
     * @param username - the username */
    public void logoutAfterException(String username){
        logout(username);
    }


    /** Get instance */
    public static ServerServiceCommunicator getInstance(){
        if (serverServiceCommunicator == null){
            serverServiceCommunicator = new ServerServiceCommunicator();
        }
        return serverServiceCommunicator;
    }

    /** Constructor */
    private ServerServiceCommunicator(){
        try {
            battleServices = battleServiceDAO.getAll();
        }
        catch (Exception e){
            e.printStackTrace();
            battleServices = new ArrayList<>();
        }
    }

}
