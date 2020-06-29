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
                // Remove ship from planet
                List<Ship> currentShips = s.getPlanet().getShips();
                currentShips.remove(s);
                s.getPlanet().setShips(currentShips);
                // Set ship to target planet
                s.setPlanet(destination);
                // Set planet ship list
                currentShips = destination.getShips();
                currentShips.add(s);
                destination.setShips(currentShips);
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
