package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.PlanetRewardController;
import com.galaxytrucker.galaxytruckerreloaded.Controller.TravelController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlanetRewardControllerTest {
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
     * get the rewards
     */
    @Test
    public void getRewardsSuccess() {
        PlanetRewardController controller1 = PlanetRewardController.getInstance(null);

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

        controller.travel(tp);

        boolean success = controller1.getRewards();
        Assert.assertTrue(success);
    }

    /**
     * reset the rewards
     */
    @Test
    public void resetRewardsSuccess() {
        PlanetRewardController controller1 = PlanetRewardController.getInstance(null);

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

        controller.travel(tp);
        controller1.getRewards();
        controller1.resetRewards();

        Assert.assertEquals(0, controller1.getFuelReward());
        Assert.assertEquals(0, controller1.getMoneyReward());
        Assert.assertEquals(0, controller1.getRocketReward());
        Assert.assertNull(controller1.getCrewReward());
        Assert.assertEquals(0, controller1.getWeaponRewards().size());
    }
}
