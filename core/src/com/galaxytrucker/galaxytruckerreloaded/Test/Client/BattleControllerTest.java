package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.BattleController;
import com.galaxytrucker.galaxytruckerreloaded.Controller.TravelController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

public class BattleControllerTest {

    private ShipDAO shipDAO = ShipDAO.getInstance();

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
     * add action
     */
    @Test
    public void playMovesSuccess() {
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        TravelController controller1 = TravelController.getInstance(null);

        boolean battle = false;
        while(!battle) {
            for (Planet p : o.getPlanetMap()) {
                if(!p.getShips().contains(s)) {
                    int distanceX = Math.round(p.getPosX() - s.getPlanet().getPosX());
                    int distanceY = Math.round(p.getPosY() - s.getPlanet().getPosY());
                    if ((Math.abs(distanceX) <= 1 && Math.abs(distanceY) <= 1)) {
                        controller1.travel(p);
                        if(p.getEvent()== PlanetEvent.COMBAT) {
                            battle = true;
                            break;
                        }
                    }
                }
            }
            o = ClientControllerCommunicator.getInstance(null).getMap();
            s = ClientControllerCommunicator.getInstance(null).getClientShip();
        }

        BattleController controller = BattleController.getInstance(null);

        s = ClientControllerCommunicator.getInstance(null).getClientShip();
        Weapon w = new Weapon();
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                w = ((System) r).getShipWeapons().get(0);
                break;
            }
        }

        Ship op = new Ship();
        for(Ship t : s.getPlanet().getShips()) {
            if(t.getId() != s.getId()) {
                op = t;
                break;
            }
        }

        controller.attack(w, op.getSystems().get(0));

        boolean success = controller.playMoves();
        Assert.assertTrue(success);
    }

    /**
     * fetch updated data
     */
    @Test
    public void fetchUpdatedDataSuccess() {
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        TravelController controller1 = TravelController.getInstance(null);

        boolean battle = false;
        while(!battle) {
            for (Planet p : o.getPlanetMap()) {
                if(!p.getShips().contains(s)) {
                    int distanceX = Math.round(p.getPosX() - s.getPlanet().getPosX());
                    int distanceY = Math.round(p.getPosY() - s.getPlanet().getPosY());
                    if ((Math.abs(distanceX) <= 1 && Math.abs(distanceY) <= 1)) {
                        controller1.travel(p);
                        if(p.getEvent()== PlanetEvent.COMBAT) {
                            battle = true;
                            break;
                        }
                    }
                }
            }
            o = ClientControllerCommunicator.getInstance(null).getMap();
            s = ClientControllerCommunicator.getInstance(null).getClientShip();
        }

        BattleController controller = BattleController.getInstance(null);

        s = ClientControllerCommunicator.getInstance(null).getClientShip();
        Weapon w = new Weapon();
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                w = ((System) r).getShipWeapons().get(0);
                break;
            }
        }

        Ship op = new Ship();
        for(Ship t : s.getPlanet().getShips()) {
            if(t.getId() != s.getId()) {
                op = t;
                break;
            }
        }

        controller.attack(w, op.getSystems().get(0));

        controller.playMoves();

        boolean success = controller.fetchUpdatedData();
        Assert.assertTrue(success);
    }

    /**
     * flee
     */
    @Test
    public void fleeSuccess() {

        BattleController controller = BattleController.getInstance(null);

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

        s.setFTLCharge(100);
        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.fleeFight(tp);
        Assert.assertTrue(success);
    }

    /**
     * try fleeing without fuel
     */
    @Test
    public void fleeNoFuel() {
        BattleController controller = BattleController.getInstance(null);

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

        boolean success = controller.fleeFight(tp);
        Assert.assertFalse(success);
    }

    /**
     * try fleeing without ftl charge
     */
    @Test
    public void fleeNoFTL() {
        BattleController controller = BattleController.getInstance(null);

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

        boolean success = controller.fleeFight(tp);
        Assert.assertFalse(success);
    }

    /**
     * try fleeing to strange planet
     */
    @Test
    public void fleeStrangePlanet() {
        BattleController controller = BattleController.getInstance(null);

        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Planet tp = new Planet();
        tp.setId(UUID.randomUUID().hashCode());

        s.setFTLCharge(100);
        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.fleeFight(tp);
        Assert.assertFalse(success);
    }

    /**
     * attack
     */
    @Test
    public void attackSuccess() {
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        TravelController controller1 = TravelController.getInstance(null);

        boolean battle = false;
        while(!battle) {
            for (Planet p : o.getPlanetMap()) {
                    if(!p.getShips().contains(s)) {
                        int distanceX = Math.round(p.getPosX() - s.getPlanet().getPosX());
                        int distanceY = Math.round(p.getPosY() - s.getPlanet().getPosY());
                        if ((Math.abs(distanceX) <= 1 && Math.abs(distanceY) <= 1)) {
                            controller1.travel(p);
                            if(p.getEvent()== PlanetEvent.COMBAT) {
                                battle = true;
                                break;
                            }
                        }
                    }
            }
            o = ClientControllerCommunicator.getInstance(null).getMap();
            s = ClientControllerCommunicator.getInstance(null).getClientShip();
        }

        BattleController controller = BattleController.getInstance(null);

        s = ClientControllerCommunicator.getInstance(null).getClientShip();
        Weapon w = new Weapon();
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                w = ((System) r).getShipWeapons().get(0);
                break;
            }
        }

        Ship op = new Ship();
        for(Ship t : s.getPlanet().getShips()) {
            if(t.getId() != s.getId()) {
                op = t;
                break;
            }
        }

        boolean success = controller.attack(w, op.getSystems().get(0));
        Assert.assertTrue(success);
    }
}
