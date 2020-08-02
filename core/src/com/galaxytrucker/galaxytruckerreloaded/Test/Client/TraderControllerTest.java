package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.TraderController;
import com.galaxytrucker.galaxytruckerreloaded.Controller.TravelController;
import com.galaxytrucker.galaxytruckerreloaded.Controller.WeaponController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.CrewStat;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.TraderDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TraderControllerTest {

    private TraderDAO traderDAO = TraderDAO.getInstance();

    private WeaponDAO weaponDAO = WeaponDAO.getInstance();

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
     * successfully purchase weapon
     */@Test
    public void purchaseWeaponSuccess() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        w.setWeaponPrice(1);
        w.setWeaponLevel(4);
        List<Integer> prices = new ArrayList<>();
        prices.add(2);
        prices.add(4);
        prices.add(6);
        prices.add(8);
        prices.add(10);
        prices.add(12);
        w.setPrice(prices);
        try {
            weaponDAO.persist(w);
        }
        catch (Exception e) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        List<Weapon> ws = new ArrayList<>();
        ws.add(w);
        t.setWeaponStock(ws);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseWeapon(t, w);
        Assert.assertTrue(success);
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertEquals(s1.getInventory().get(0).getId(), w.getId());
        Assert.assertEquals(s.getCoins()-19, s1.getCoins());
    }

    /**
     * successfully purchase rockets
     */
    @Test
    public void purchaseRocketsSuccess() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(5);
        try {
            traderDAO.persist(t);
        }
        catch (Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseRockets(t, 2);
        Assert.assertTrue(success);
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertEquals(s.getMissiles()+2, s1.getMissiles());
        Assert.assertEquals(s.getCoins() - 12, s1.getCoins());
    }

    /**
     * purchase hp
     */
    @Test
    public void purchaseHpSuccess() {

        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setHpStock(5);
        try {
            traderDAO.persist(t);
        }
        catch (Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseHP(t, 2);
        Assert.assertTrue(success);
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertEquals(s.getHp()+2, s1.getHp());
        Assert.assertEquals(s.getCoins() - 4, s1.getCoins());
    }

    /**
     * purchase fuel
     */
    @Test
    public void purchaseFuelSuccess() {

        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setFuelStock(5);
        try {
            traderDAO.persist(t);
        }
        catch (Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseFuel(t, 2);
        Assert.assertTrue(success);
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertEquals(s.getFuel()+2, s1.getFuel());
        Assert.assertEquals(s.getCoins() - 6, s1.getCoins());
    }

    /**
     * upgrade crew
     */
    @Test
    public void upgradeCrewSuccess() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Crew c = new Crew();
        for(Room r : s.getSystems()) {
            if(r.getCrew() != null && r.getCrew().size() > 0) {
                c = r.getCrew().get(0);
                break;
            }
        }

        boolean success = controller.upgradeCrew(c, CrewStat.COMBAT);
        Assert.assertTrue(success);
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertEquals(s.getCoins()-20, s1.getCoins());

        Crew c1 = new Crew();
        for(Room r : s1.getSystems()) {
            if(r.getCrew() != null && r.getCrew().size() > 0) {
                for(Crew cs : r.getCrew()) {
                    if(cs.getId() == c.getId()) {
                        c1 = cs;
                    }
                }
            }
        }
        Assert.assertEquals((long) c.getStats().get(4)+1, (long) c1.getStats().get(4));
    }

    /**
     * purchase Crew
     */
    @Test
    public void purchaseCrewSuccess() {
    }

    /**
     * sell weapon
     */
    @Test
    public void sellWeaponSuccess() {
        TraderController controller = TraderController.getInstance(null);
        WeaponController controller1 = WeaponController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        try {
            traderDAO.persist(t);
        }
        catch (Exception e) {
            Assert.fail();
        }

        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System)r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                controller1.unequipWeapon(((System) r).getShipWeapons().get(0));
                break;
            }
        }

        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();

        Weapon w = s1.getInventory().get(0);

        boolean success = controller.sellWeapon(t, w);
        Assert.assertTrue(success);
        Ship s2 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertTrue(s2.getInventory().isEmpty());
    }

    /**
     * sell rocket
     */
    @Test
    public void sellRocketSuccess() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        try {
            traderDAO.persist(t);
        }
        catch (Exception e) {
            Assert.fail();
        }

        boolean success = controller.sellRockets(t, 2);
        Assert.assertTrue(success);
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertEquals(s.getMissiles()-2, s1.getMissiles());
    }
}
