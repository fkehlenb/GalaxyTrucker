package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.CrewController;
import com.galaxytrucker.galaxytruckerreloaded.Controller.TravelController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

public class TravelControllerTest {

    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * setup
     */
    @BeforeClass
    public static void setup() {

        Server.getInstance("localhost", 5050);
        Client client = new Client("localhost", 5050);
        ClientControllerCommunicator.getInstance(client);
        ClientControllerCommunicator.getInstance(null).login("newuser", ShipType.DEFAULT, 1);
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

    /**
     * try travelling without fuel
     */
    @Test
    public void travelNoFuel() {
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

        s.setFuel(0);
        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.travel(tp);
        Assert.assertFalse(success);
    }

    /**
     * try travelling without ftl charge
     */
    @Test
    public void travelNoFTL() {
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

        s.setFTLCharge(0);
        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.travel(tp);
        Assert.assertFalse(success);
    }

    /**
     * try travelling to unknown planet
     */
    @Test
    public void travelStrangePlanet() {
        TravelController controller = TravelController.getInstance(null);
        Planet tp = new Planet();
        tp.setId(UUID.randomUUID().hashCode());

        boolean success = controller.travel(tp);
        Assert.assertFalse(success);
    }

    /**
     * try travelling without anyone in cockpit
     */
    @Test
    public void travelNooneInCockpit() {
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

        CrewController crewc = CrewController.getInstance(null);
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.COCKPIT && r.getCrew() != null && r.getCrew().size() > 0) {
                for(Crew c : r.getCrew()) {
                    crewc.moveCrewToRoom(c, s.getSystems().get(0));
                }
            }
        }

        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.travel(tp);
        Assert.assertFalse(success);
    }

}
