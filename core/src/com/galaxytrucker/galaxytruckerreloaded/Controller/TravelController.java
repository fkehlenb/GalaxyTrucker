package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelController extends Controller{

    /** ClientControllerCommunicator */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

    /** Instance */
    private static TravelController singleton;

    /** Get controller instance */
    public static TravelController getInstance(ClientControllerCommunicator communicator) {
        if(singleton == null) {
            singleton = new TravelController(communicator);
        }
        return singleton;
    }

    public static void remove() {
        singleton = null;
    }

    /**
     * travels from one location to another
     * @param destination - the destination
     */
    public boolean travel(Planet destination){
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestType(RequestType.HYPERJUMP);
            requestObject.setShip(clientControllerCommunicator.getClientShip());
            requestObject.setPlanet(destination);
            ResponseObject object = clientControllerCommunicator.sendRequest(requestObject);
            if (object.isValidRequest()){
                clientControllerCommunicator.setClientShip(object.getResponseShip());
                clientControllerCommunicator.setMap(object.getResponseOverworld());
                return true;
            }
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
