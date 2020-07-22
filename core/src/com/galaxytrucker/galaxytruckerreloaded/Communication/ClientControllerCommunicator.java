package com.galaxytrucker.galaxytruckerreloaded.Communication;

import com.galaxytrucker.galaxytruckerreloaded.Controller.*;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientControllerCommunicator {

    /** Singleton */
    private static ClientControllerCommunicator singleton = null;

    /**
     * Client ship
     */
    private Ship clientShip;

    /**
     * OverWorld
     */
    private Overworld map;

    /**
     * Client class
     */
    @NonNull
    private Client client;

    /**
     * Issue a new request and receive a response
     *
     * @param request - the request
     * @return the server's response
     */
    public ResponseObject sendRequest(RequestObject request) throws IllegalArgumentException {
        return client.sendAndReceive(request);
    }

    /**
     * Issue login request
     *
     * @param username - the username
     * @return true if the user already exists else create a enw spaceship
     */
    public boolean login(String username, ShipType ship, int difficulty) {
        boolean permittedLogin = client.login(username, ship, difficulty);
        if (permittedLogin) {
            this.clientShip = client.getMyShip();
            this.map = client.getOverworld();
        }
        return permittedLogin;
    }

    /**
     * Issue logout request
     * @return true if the user was succesfully logged out
     */
    public boolean logout() {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.LOGOUT);
            requestObject.setShip(clientShip);
            System.out.println("<Client>:[Name]:" + clientShip.getAssociatedUser() + ":[Logout]:[Request]");
            ResponseObject responseObject = sendRequest(requestObject);
            System.out.println("<Client>:[Name]:" + clientShip.getAssociatedUser() + ":[Logout]:" + responseObject.isValidRequest());
            return responseObject.isValidRequest();
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Constructor
     * @param client - the client object, used to connect to server */
    public static ClientControllerCommunicator getInstance(Client client){
        if (singleton == null){
            singleton = new ClientControllerCommunicator(client);
        }
        CrewController.getInstance(singleton);
        HangerController.getInstance();
        TraderController.getInstance(singleton);
        TravelController.getInstance(singleton);
        PlanetEventController.getInstance(singleton);
        SystemController.getInstance(singleton);
        AudioController.getInstance();
        // TODO CREATE ALL CONTROLLERS HERE, all controllers should be singletons
        return singleton;
    }
}
