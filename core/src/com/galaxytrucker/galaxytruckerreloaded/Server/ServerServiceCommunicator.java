package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.j256.ormlite.support.ConnectionSource;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ServerServiceCommunicator {

    /** Database connection source */
    @NonNull
    private ConnectionSource databaseConnectionSource;

    /** Take a request from the client side, pass it through the services
     * and return a response
     * @return the server's response to the client's request */
    public String getResponse(String request){
        return null;
    }

    /** Send the client his ship
     * @param username - the client's username
     * @return the client's ship */
    public Ship getClientShip(String username){
        return null;
    }

    /** Login
     * @param username - the user that wants to login
     * @return if the client is allowed to login */
    public String login(String username){
        return null;
    }

}
