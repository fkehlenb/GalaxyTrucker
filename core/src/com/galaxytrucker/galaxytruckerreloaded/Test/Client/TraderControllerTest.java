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
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.*;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
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

    private ShipDAO shipDAO = ShipDAO.getInstance();

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
     * try purchasing without money
     */
    @Test
    public void purchaseWeaponNoMoney() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        w.setWeaponPrice(10000);
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
        Assert.assertFalse(success);
    }

    /**
     * try purchasing non existing weapon
     */
    @Test
    public void purchaseWeaponNotThere() {
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

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        List<Weapon> ws = new ArrayList<>();
        t.setWeaponStock(ws);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseWeapon(t, w);
        Assert.assertFalse(success);
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
     * try purchasing without money
     */
    @Test
    public void purchaseRocketsNoMoney() {
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

        s.setCoins(0);
        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseRockets(t, 2);
        Assert.assertFalse(success);
    }

    /**
     * try purchasing too much
     */
    @Test
    public void purchaseRocketsTooMuch() {
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

        boolean success = controller.purchaseRockets(t, 10);
        Assert.assertFalse(success);
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
     * try purchasing without money
     */
    @Test
    public void purchaseHpNoMoney() {
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

        s.setCoins(0);
        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseHP(t, 2);
        Assert.assertFalse(success);
    }

    /**
     * try purchasing too much
     */
    @Test
    public void purchaseHpTooMuch() {
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

        boolean success = controller.purchaseHP(t, 10);
        Assert.assertFalse(success);
    }

    /**
     * purchase fuel
     */
    @Test
    public void purchaseFuelNoMoney() {

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

        s.setCoins(0);
        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseFuel(t, 2);
        Assert.assertFalse(success);
    }

    /**
     * try purchasing without money
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
     * try purchasing too much
     */
    @Test
    public void purchaseFuelTooMuch() {
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

        boolean success = controller.purchaseFuel(t, 10);
        Assert.assertFalse(success);
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
     * try upgrading crew without money
     */
    @Test
    public void upgradeCrewNoMoney() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Crew c = new Crew();
        for(Room r : s.getSystems()) {
            if(r.getCrew() != null && r.getCrew().size() > 0) {
                c = r.getCrew().get(0);
                break;
            }
        }

        s.setCoins(0);
        try {
            shipDAO.update(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.upgradeCrew(c, CrewStat.COMBAT);
        Assert.assertFalse(success);
    }

    /**
     * try upgrading unknown crew member
     */
    @Test
    public void upgradeStrangeCrew() {
        TraderController controller = TraderController.getInstance(null);

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(5);

        boolean success = controller.upgradeCrew(c, CrewStat.COMBAT);
        Assert.assertFalse(success);
    }

    /**
     * purchase Crew
     */
    @Test
    public void purchaseCrewSuccess() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(5);
        try {
            crewDAO.persist(c);
        }
        catch (Exception e) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        List<Crew> cs = new ArrayList<>();
        cs.add(c);
        t.setCrewStock(cs);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseCrew(t, c);
        Assert.assertTrue(success);
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        //Assert.assertEquals(s.getCoins()-5, s1.getCoins());
        for(Room r : s1.getSystems()) {
            if(r.getCrew()!=null) {
                for(Crew g : r.getCrew()) {
                    if(g.getId() == c.getId()) {
                        Assert.assertTrue(true);
                        Assert.assertNotNull(g.getCurrentRoom());
                        Assert.assertNotNull(g.getTile());
                        break;
                    }
                }
            }
        }
    }

    /**
     * try purchasing without money
     */
    @Test
    public void purchaseCrewNoMoney() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(5000);
        try {
            crewDAO.persist(c);
        }
        catch (Exception e) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        List<Crew> cs = new ArrayList<>();
        cs.add(c);
        t.setCrewStock(cs);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseCrew(t, c);
        Assert.assertFalse(success);
    }

    /**
     * try purchasing unknown crew
     */
    @Test
    public void purchaseNonExistingCrew() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(5);

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        List<Crew> cs = new ArrayList<>();
        t.setCrewStock(cs);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        boolean success = controller.purchaseCrew(t, c);
        Assert.assertFalse(success);
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
     * try selling unknown weapon
     */
    @Test
    public void sellStrangeWeapon() {
        TraderController controller = TraderController.getInstance(null);

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        try {
            traderDAO.persist(t);
        }
        catch (Exception e) {
            Assert.fail();
        }

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        w.setWeaponPrice(10);
        w.setWeaponLevel(4);
        List<Integer> prices = new ArrayList<>();
        prices.add(2);
        prices.add(4);
        prices.add(6);
        prices.add(8);
        prices.add(10);
        prices.add(12);
        w.setPrice(prices);

        boolean success = controller.sellWeapon(t, w);
        Assert.assertFalse(success);
    }

    /**
     * try selling to unknown trader
     */
    @Test
    public void sellWeaponUnknownTrader() {
        TraderController controller = TraderController.getInstance(null);
        WeaponController controller1 = WeaponController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());

        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System)r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                controller1.unequipWeapon(((System) r).getShipWeapons().get(0));
                break;
            }
        }

        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();

        Weapon w = s1.getInventory().get(0);

        boolean success = controller.sellWeapon(t, w);
        Assert.assertFalse(success);
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

    /**
     * try selling more than you have
     */
    @Test
    public void sellRocketTooMuch() {
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

        boolean success = controller.sellRockets(t, 200000);
        Assert.assertFalse(success);
    }

    /**
     * try selling to unknown trader
     */
    @Test
    public void sellRocketsUnknownTrader() {
        TraderController controller = TraderController.getInstance(null);
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());

        boolean success = controller.sellRockets(t, 2);
        Assert.assertFalse(success);
    }
}
