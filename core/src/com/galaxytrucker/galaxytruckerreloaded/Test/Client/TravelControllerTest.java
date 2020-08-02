package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.TravelController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TravelControllerTest {
    /**
     * setup
     */
    @BeforeClass
    public static void setup() {

        Server.getInstance("localhost", 5050);
        Client client = new Client("localhost", 5050);
        ClientControllerCommunicator.getInstance(client);
        ClientControllerCommunicator.getInstance(null).login("newuser", ShipType.STEALTH, 1);
    }

    /**
     * travel to another planet
     */
    @Test
    public void travelSuccess() {
        TravelController controller = TravelController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        Planet tp = new Planet();
        for(Planet p : o.getPlanetMap()) {
            if(!p.getShips().contains(s)) {
                int distanceX = Math.round(p.getPosX() - s.getPlanet().getPosX());
                int distanceY = Math.round(p.getPosY() - s.getPlanet().getPosY());
                if((Math.abs(distanceX) <= 1 && Math.abs(distanceY) <= 1)) {
                    tp = p;
                }
            }
        }

        boolean success = controller.travel(tp);
        Assert.assertTrue(success);
        Assert.assertEquals(s.getId(), ClientControllerCommunicator.getInstance(null).getClientShip().getId());
        Assert.assertEquals(o.getId(), ClientControllerCommunicator.getInstance(null).getMap().getId());
        Assert.assertEquals(tp.getId(), ClientControllerCommunicator.getInstance(null).getClientShip().getPlanet().getId());
        Assert.assertEquals(s.getFuel()-1, ClientControllerCommunicator.getInstance(null).getClientShip().getFuel());

    }

}
