package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.SystemController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SystemControllerTest {

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
     * test successful adding of energy
     */
    @Test
    public void addEnergySuccess() {

        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.ENGINE) {
                s = (System) r;
            }
        }

        boolean success = controller.addEnergy(s, 1);
        Assert.assertTrue(success);
        Assert.assertEquals(ship.getId(), ClientControllerCommunicator.getInstance(null).getClientShip().getId());
        System s1 = new System();
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.ENGINE) {
                s1 = (System) r;
                break;
            }
        }
        Assert.assertEquals(s.getEnergy()+1, s1.getEnergy());
        Assert.assertEquals(o.getId(), ClientControllerCommunicator.getInstance(null).getMap().getId());
        Assert.assertEquals(ship.getEnergy()-1, ClientControllerCommunicator.getInstance(null).getClientShip().getEnergy());
    }

    /**
     * try adding too much energy
     */
    @Test
    public void addEnergyTooMuch() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.ENGINE) {
                s = (System) r;
            }
        }

        boolean success = controller.addEnergy(s, 100);
        Assert.assertFalse(success);
    }

    /**
     * try adding energy to cockpit
     */
    @Test
    public void addEnergyCockpit() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.COCKPIT) {
                s = (System) r;
            }
        }

        boolean success = controller.addEnergy(s, 1);
        Assert.assertFalse(success);
    }

    /**
     * try adding energy to cameras
     */
    @Test
    public void addEnergyCameras() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.CAMERAS) {
                s = (System) r;
            }
        }

        boolean success = controller.addEnergy(s, 1);
        Assert.assertFalse(success);
    }

    /**
     * test successful removing of energy
     */
    @Test
    public void removeEnergySuccess() {

        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.ENGINE) {
                s = (System) r;
            }
        }

        boolean success = controller.removeEnergy(s, 1);
        Assert.assertTrue(success);
        Assert.assertEquals(ship.getId(), ClientControllerCommunicator.getInstance(null).getClientShip().getId());
        System s1 = new System();
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.ENGINE) {
                s1 = (System) r;
                break;
            }
        }
        Assert.assertEquals(s.getEnergy()-1, s1.getEnergy());
        Assert.assertEquals(o.getId(), ClientControllerCommunicator.getInstance(null).getMap().getId());
        Assert.assertEquals(ship.getEnergy()+1, ClientControllerCommunicator.getInstance(null).getClientShip().getEnergy());
    }

    /**
     * try removing energy to cockpit
     */
    @Test
    public void removeEnergyCockpit() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.COCKPIT) {
                s = (System) r;
            }
        }

        boolean success = controller.removeEnergy(s, 1);
        Assert.assertFalse(success);
    }

    /**
     * try removing energy to cameras
     */
    @Test
    public void removeEnergyCameras() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.CAMERAS) {
                s = (System) r;
            }
        }

        boolean success = controller.removeEnergy(s, 1);
        Assert.assertFalse(success);
    }

    /**
     * try upgrading system that is not there
     * needs to be executed before install stuff
     */
    @Test
    public void upgradeSystemNotThere() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.SHIELDS) {
                s = (System) r;
            }
        }

        boolean success = controller.upgradeSystem(s);
        Assert.assertFalse(success);
    }

    /**
     * install system
     */
    @Test
    public void installSystemSuccess() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();

        boolean success = controller.installSystem(SystemType.SHIELDS);
        Assert.assertTrue(success);

        Assert.assertEquals(ship.getId(), ClientControllerCommunicator.getInstance(null).getClientShip().getId());
        System s1 = new System();
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.SHIELDS) {
                s1 = (System) r;
                break;
            }
        }
        Assert.assertEquals(o.getId(), ClientControllerCommunicator.getInstance(null).getMap().getId());
        Assert.assertTrue(s1.isUnlocked());
        Assert.assertTrue(s1.isDisabled());
    }

    /**
     * try installing system that is already there
     */
    @Test
    public void installSystemAlreadyThere() {
        SystemController controller = SystemController.getInstance(null);
        boolean success = controller.installSystem(SystemType.ENGINE);
        Assert.assertFalse(success);
    }

    /**
     * upgrade a system
     */
    @Test
    public void upgradeSystemSuccess() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.O2) {
                s = (System) r;
            }
        }

        boolean success = controller.upgradeSystem(s);
        Assert.assertTrue(success);
        Assert.assertEquals(ship.getId(), ClientControllerCommunicator.getInstance(null).getClientShip().getId());
        System s1 = new System();
        for(Room r : ClientControllerCommunicator.getInstance(null).getClientShip().getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.O2) {
                s1 = (System) r;
                break;
            }
        }
        Assert.assertEquals(o.getId(), ClientControllerCommunicator.getInstance(null).getMap().getId());
        Assert.assertEquals(s.getMaxEnergy()+1, s1.getMaxEnergy());
    }

    /**
     * try upgrading system that is already maxed out
     */
    @Test
    public void upgradeSystemMax() {
        SystemController controller = SystemController.getInstance(null);
        Ship ship = ClientControllerCommunicator.getInstance(null).getClientShip();
        Overworld o = ClientControllerCommunicator.getInstance(null).getMap();
        System s = new System();
        for(Room r : ship.getSystems()) {
            if(r.isSystem() && ((System) r).getSystemType() == SystemType.ENGINE) {
                s = (System) r;
            }
        }

        boolean success = controller.upgradeSystem(s);
        Assert.assertFalse(success);
    }
}
