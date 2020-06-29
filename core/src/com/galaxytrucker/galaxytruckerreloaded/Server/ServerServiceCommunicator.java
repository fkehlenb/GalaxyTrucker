package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.TravelService;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** ServerServiceCommunicator for executing logic using services, singleton */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class ServerServiceCommunicator {

    /** ServerServiceCommunicator */
    private static ServerServiceCommunicator serverServiceCommunicator = null;

    /** User service */
    private UserService userService = new UserService();

    /** TravelService */
    private TravelService travelService = new TravelService();

    /** Take a request from the client side, pass it through the services
     * and return a response
     * @return the server's response to the client's request */
    public ResponseObject getResponse(RequestObject request){
        switch (request.getRequestType()){
            case LOGOUT:
                return logout(request.getShip().getAssociatedUser());
            case HYPERJUMP:
                return jump(request.getShip(),request.getPlanet());
                //TODO OTHERS
        }
        return null;
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

    // ==================================== TRAVEL SERVICE ====================================

    /** Make a jump to a target planet
     * @param s - the client ship
     * @param p - the target planet
     * @return a ResponseObject */
    private ResponseObject jump(Ship s, Planet p){
        ResponseObject responseObject = new ResponseObject();
        try {
            if (travelService.validateJump(s, p,userService.getUser(s.getAssociatedUser()))) {
                boolean successfulJump = travelService.jump(s, p);
                if (successfulJump) {
                    responseObject.setValidRequest(true);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    // ========================================================================================

    /** Get instance */
    public static ServerServiceCommunicator getInstance(){
        if (serverServiceCommunicator == null){
            serverServiceCommunicator = new ServerServiceCommunicator();
        }
        return serverServiceCommunicator;
    }

}
