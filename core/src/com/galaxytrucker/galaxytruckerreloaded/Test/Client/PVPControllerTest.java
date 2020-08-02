package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.PVPController;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PVPControllerTest {
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
     * activate the pvp
     */
    @Test
    public void activateSuccess() {
        PVPController controller = PVPController.getInstance(null);
        boolean success = controller.activatePVP(ClientControllerCommunicator.getInstance(null).getClientShip());
        Assert.assertTrue(success);
        Assert.assertTrue(ClientControllerCommunicator.getInstance(null).getClientShip().isPlayingPVP());
    }
}
