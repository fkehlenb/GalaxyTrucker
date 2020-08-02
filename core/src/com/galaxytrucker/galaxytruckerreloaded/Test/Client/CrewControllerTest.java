package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.CrewController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CrewControllerTest {
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
     * move crew to room
     */
    @Test
    public void moveCrewToRoomSuccess() {
        CrewController controller = CrewController.getInstance(null);
        Crew c = new Crew();
        Room tr = new Room();

        Room er = new Room();
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()) {
            if(r.getCrew().size()>0) {
                c = r.getCrew().get(0);
                tr = r;
            }
            if(c.getAssociatedUser() != null && r.getId() != tr.getId()) {
                er = r;
            }
        }
        boolean success = controller.moveCrewToRoom(c, er);
        Assert.assertTrue(success);
    }
}
