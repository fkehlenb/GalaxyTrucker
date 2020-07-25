package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CrewController extends Controller {

    private static CrewController singleton;

    /**
     * ClientControllerCommunicator
     */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /**
     * return the instance of this singleton
     * @param communicator the communicator
     * @return the singleton instance
     */
    public static CrewController getInstance(ClientControllerCommunicator communicator) {
        if(singleton == null) {
            singleton = new CrewController(communicator);
        }
        return singleton;
    }

    /**
     * Move a crew member to a different section
     *
     * @param crew - the crew member
     * @param room - the room to move him to
     */
    public boolean moveCrewToRoom(Crew crew, Room room) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.MoveCrew);
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setCrew(crew);
            requestObject.setRoom(room);
            ResponseObject responseObject = clientControllerCommunicator.sendRequest(requestObject);
            if (responseObject.isValidRequest()) {
                clientControllerCommunicator.setClientShip(responseObject.getResponseShip());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
