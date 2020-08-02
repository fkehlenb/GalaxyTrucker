package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.CrewController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

public class CrewControllerTest {

    private CrewDAO crewDAO = CrewDAO.getInstance();

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
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        for(Room r : s1.getSystems()) {
            if(r.getCrew() != null) {
                for(Crew cs : r.getCrew()) {
                    if(cs.getId() == c.getId()) {
                        Assert.assertEquals(er.getId(), r.getId());
                        Assert.assertEquals(er.getId(), cs.getCurrentRoom().getId());
                        break;
                    }
                }
            }
        }
    }

    /**
     * try moving crew to same room
     *
     */
    @Test
    public void moveCrewSameRoom() {
        CrewController controller = CrewController.getInstance(null);
        Crew c = new Crew();
        Room tr = new Room();
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()) {
            if(r.getCrew().size()>0) {
                c = r.getCrew().get(0);
                tr = r;
            }
        }
        boolean success = controller.moveCrewToRoom(c, tr);
        Assert.assertFalse(success);
    }

    /**
     * move crew that is not in ship
     */
    @Test
    public void moveCrewNotIn() {
        CrewController controller = CrewController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();
        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        try {
            crewDAO.persist(c);
        }
        catch(Exception e) {
            Assert.fail();
        }
        boolean success = controller.moveCrewToRoom(c, s.getSystems().get(0));
        Assert.assertFalse(success);
    }
}
