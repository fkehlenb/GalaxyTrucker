package com.galaxytrucker.galaxytruckerreloaded.Controller.Actions;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientControllerCommunicator {

    /** Client ship */
    private Ship clientShip;

    /** Issue a new request and receive a response
     * @param request - the request
     * @return the server's response */
    public String sendRequest(String request){
        return null;
    }

    /** Issue login request
     * @param username - the username
     * @return true if the user already exists else create a enw spaceship */
    public boolean login(String username){
        return false;
    }
}
