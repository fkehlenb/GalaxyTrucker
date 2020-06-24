package com.galaxytrucker.galaxytruckerreloaded.Communication;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientControllerCommunicator {

    /**
     * Client ship
     */
    private Ship clientShip;
    private Overworld map;
    private Planet currentPlanet;

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
    public boolean login(String username) {
        boolean permittedLogin = client.login(username, ShipType.DEFAULT);
        if (permittedLogin) {
            this.clientShip = client.getMyShip();
            this.map = client.getOverworld();
        }
        return permittedLogin;
    }
}
