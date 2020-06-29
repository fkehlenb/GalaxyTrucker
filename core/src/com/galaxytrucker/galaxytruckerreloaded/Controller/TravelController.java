package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TravelController extends Controller{

    /** ClientControllerCommunicator */
    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;

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
                Ship s = clientControllerCommunicator.getClientShip();
                s.setPlanet(destination);
                clientControllerCommunicator.setClientShip(s);
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
