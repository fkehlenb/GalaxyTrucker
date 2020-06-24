package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
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

    /** Take a request from the client side, pass it through the services
     * and return a response
     * @return the server's response to the client's request */
    public String getResponse(RequestObject request){
        return null;
    }

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
                userService.addUser(username);
                return false;
            }
            catch (Exception f){
                f.printStackTrace();
                throw new IllegalArgumentException();
            }
        }
    }

    /** Get instance */
    public static ServerServiceCommunicator getInstance(){
        if (serverServiceCommunicator == null){
            serverServiceCommunicator = new ServerServiceCommunicator();
        }
        return serverServiceCommunicator;
    }

}
