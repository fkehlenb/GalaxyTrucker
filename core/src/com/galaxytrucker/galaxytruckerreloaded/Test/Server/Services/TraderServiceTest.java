package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.CrewStat;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.TraderDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.TraderService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TraderServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private TraderService service = new TraderService();

    private ShipDAO shipDAO = new ShipDAO();

    private TraderDAO traderDAO = new TraderDAO();

    private WeaponDAO weaponDAO = new WeaponDAO();

    private CrewDAO crewDAO = new CrewDAO();

    /**
     * successfully purchase weapon
     */
    @Test
    public void purchaseWeaponSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

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

        ResponseObject response = service.purchaseWeapon(s, t, w);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Trader t1 = entityManager.find(Trader.class, t.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(180, s1.getCoins());
        Assert.assertEquals(0, t1.getWeaponStock().size());
        Assert.assertTrue(s1.getInventory().contains(w));
    }

    /**
     * try buying weapon not in trader
     */
    @Test
    public void purchaseWeaponWNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

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
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseWeapon(s, t, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing from non existing trader
     */
    @Test
    public void purchaseWeaponTNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

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

        ResponseObject response = service.purchaseWeapon(s, t, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing when ship does not exist
     */
    @Test
    public void purchaseWeaponSNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);

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

        ResponseObject response = service.purchaseWeapon(s, t, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try buying weapon without money
     */
    @Test
    public void purchaseWeaponNoMoney() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(0);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

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

        ResponseObject response = service.purchaseWeapon(s, t, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * attempt purchasing a weapon without any place left
     */
    @Test
    public void purchaseWeaponNoPlace() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        List<Weapon> inventory = new ArrayList<>();
        for(int i = 0; i<5; i++) {
            Weapon iw = new Weapon();
            iw.setId(UUID.randomUUID().hashCode());
            inventory.add(iw);
        }
        s.setInventory(inventory);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

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

        ResponseObject response = service.purchaseWeapon(s, t, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * successfully purchase crew
     */
    @Test
    public void purchaseCrewSuccess() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));

        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(100);
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

        ResponseObject response = service.purchaseCrew(s, t, c);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Trader t1 = entityManager.find(Trader.class, t.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(99, s1.getCoins());
        Assert.assertEquals(0, t1.getCrewStock().size());
        Assert.assertTrue(s1.getSystems().get(0).getCrew().contains(c));
    }

    /**
     * try purchasing non existing crew
     */
    @Test
    public void purchaseCrewCNotExisting() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));

        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(100);

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setCrewStock(new ArrayList<Crew>());
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseCrew(s, t, c);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try pruchasing crew when trader does not exist
     */
    @Test
    public void purchaseCrewTNotExisting() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));

        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(100);
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

        ResponseObject response = service.purchaseCrew(s, t, c);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing crew for non existing ship
     */
    @Test
    public void purchaseCrewSNotExisting() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));

        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(100);
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

        ResponseObject response = service.purchaseCrew(s, t, c);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing crew without money
     */
    @Test
    public void purchaseCrewNoMoney() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));

        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(0);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(100);
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

        ResponseObject response = service.purchaseCrew(s, t, c);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing crew without free space
     */
    @Test
    public void purchaseCrewNoSpace() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        Crew shipCrew = new Crew();
        shipCrew.setId(UUID.randomUUID().hashCode());
        try {
            crewDAO.persist(shipCrew);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        for(Tile t : tiles) {
            t.setStandingOnMe(shipCrew);
        }
        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Crew c = new Crew();
        c.setId(UUID.randomUUID().hashCode());
        c.setPrice(100);
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

        ResponseObject response = service.purchaseCrew(s, t, c);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * successfully purchase rockets
     */
    @Test
    public void purchaseRocketsSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseRockets(s, t, 2);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Trader t1 = entityManager.find(Trader.class, t.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(2, s1.getMissiles());
        Assert.assertEquals(98, t1.getMissileStock());
        Assert.assertEquals(187, s1.getCoins());
    }

    /**
     * try purchasing rockets when trader does not exist
     */
    @Test
    public void purchaseRocketsTNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);

        ResponseObject response = service.purchaseRockets(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing rockets when the ship does not exis
     */
    @Test
    public void purchaseRocketsSNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseRockets(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing rockets when the stock does not exist
     */
    @Test
    public void purchaseRocketsStockNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseRockets(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing rockets without money
     */
    @Test
    public void purchaseRocketsNoMoney() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(0);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseRockets(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing more than trader has
     */
    @Test
    public void purchaseRocketsNoStock() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(1200);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseRockets(s, t, 200);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * successfully purchase fuel
     */
    @Test
    public void purchaseFuelSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setFuelStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseFuel(s, t, 2);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Trader t1 = entityManager.find(Trader.class, t.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(2, s1.getFuel());
        Assert.assertEquals(98, t1.getFuelStock());
        Assert.assertEquals(193, s1.getCoins());
    }

    /**
     * try purchasing Fuel when trader does not exist
     */
    @Test
    public void purchaseFuelTNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setFuelStock(100);

        ResponseObject response = service.purchaseFuel(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing Fuel when the ship does not exis
     */
    @Test
    public void purchaseFuelSNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setFuelStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseFuel(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing Fuel when the stock does not exist
     */
    @Test
    public void purchaseFuelStockNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseFuel(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing more than trader has
     */
    @Test
    public void purchaseFuelNoStock() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(1200);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setFuelStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseFuel(s, t, 200);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing rockets without money
     */
    @Test
    public void purchaseFuelNoMoney() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(0);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setFuelStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseFuel(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * successfully purchase hp
     */
    @Test
    public void purchaseHPSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setHpStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseHP(s, t, 2);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Trader t1 = entityManager.find(Trader.class, t.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(2, s1.getHp());
        Assert.assertEquals(98, t1.getHpStock());
        Assert.assertEquals(195, s1.getCoins());
    }

    /**
     * try purchasing rockets when trader does not exist
     */
    @Test
    public void purchaseHpTNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setHpStock(100);

        ResponseObject response = service.purchaseHP(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing rockets when the ship does not exis
     */
    @Test
    public void purchaseHpSNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setHpStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseHP(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing Hp when the stock does not exist
     */
    @Test
    public void purchaseHpStockNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseHP(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing more than trader has
     */
    @Test
    public void purchaseHpNoStock() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(1200);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setHpStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseHP(s, t, 200);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try purchasing hp without money
     */
    @Test
    public void purchaseHpNoMoney() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(0);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setHpStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.purchaseHP(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * successfully sell weapon
     */
    @Test
    public void sellWeaponSuccess() {

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

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();
        inventory.add(w);
        s.setInventory(inventory);
        s.setCoins(199);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setWeaponStock(new ArrayList<Weapon>());
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.sellWeapon(s, t, w);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Trader t1 = entityManager.find(Trader.class, t.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(218, s1.getCoins());
        Assert.assertTrue(t1.getWeaponStock().contains(w));
        Assert.assertFalse(s1.getInventory().contains(w));
    }

    /**
     * try selling non existing weapon
     */
    @Test
    public void sellWeaponWNotExisting() {
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

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();
        s.setInventory(inventory);
        s.setCoins(199);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setWeaponStock(new ArrayList<Weapon>());
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.sellWeapon(s, t, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try selling from non existing ship
     */
    @Test
    public void sellWeaponSNotExising() {
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

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();
        inventory.add(w);
        s.setInventory(inventory);
        s.setCoins(199);

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setWeaponStock(new ArrayList<Weapon>());
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.sellWeapon(s, t, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try selling to non existing trader
     */
    @Test
    public void sellWeaponTNotExisting() {
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

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();
        inventory.add(w);
        s.setInventory(inventory);
        s.setCoins(199);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setWeaponStock(new ArrayList<Weapon>());

        ResponseObject response = service.sellWeapon(s, t, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * successfully sell rockets
     */
    @Test
    public void sellRocketsSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        s.setMissiles(10);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.sellRockets(s, t, 2);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Trader t1 = entityManager.find(Trader.class, t.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(102, t1.getMissileStock());
        Assert.assertEquals(8, s1.getMissiles());
        Assert.assertEquals(199+12, s1.getCoins());
    }

    /**
     * try selling rockets from non existing ship
     */
    @Test
    public void sellRocketsSNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        s.setMissiles(10);

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.sellRockets(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * sell rockets to non existing trader
     */
    @Test
    public void sellRocketsTNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        s.setMissiles(10);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);

        ResponseObject response = service.sellRockets(s, t, 2);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * sell more rockets than existing
     */
    @Test
    public void sellRocketsNotEnough() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        s.setMissiles(10);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Trader t = new Trader();
        t.setId(UUID.randomUUID().hashCode());
        t.setMissileStock(100);
        try {
            traderDAO.persist(t);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.sellRockets(s, t,  100);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * successfully upgrade crew
     */
    @Test
    public void upgradeCrewSuccess() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        Crew shipCrew = new Crew();
        shipCrew.setId(UUID.randomUUID().hashCode());
        List<Integer> stats = new ArrayList<>();
        for(int i=0; i<5;i++) {
            stats.add(2);
        }
        shipCrew.setStats(stats);
        try {
            crewDAO.persist(shipCrew);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        tiles.get(0).setStandingOnMe(shipCrew);
        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.upgradeCrew(s, shipCrew, CrewStat.WEAPON);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Crew c1 = entityManager.find(Crew.class, shipCrew.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(179, s1.getCoins());
        Assert.assertEquals(3, (int) c1.getStats().get(0));
    }

    /**
     * try upgrading crew of non existing ship
     */
    @Test
    public void upgradeCrewSNOtExisting() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        Crew shipCrew = new Crew();
        shipCrew.setId(UUID.randomUUID().hashCode());
        List<Integer> stats = new ArrayList<>();
        for(int i=0; i<5;i++) {
            stats.add(2);
        }
        shipCrew.setStats(stats);
        try {
            crewDAO.persist(shipCrew);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        tiles.get(0).setStandingOnMe(shipCrew);
        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");

        ResponseObject response = service.upgradeCrew(s, shipCrew, CrewStat.WEAPON);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try upgrading crew that does not exist
     */
    @Test
    public void upgradeCrewCNotExisting() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        Crew shipCrew = new Crew();
        shipCrew.setId(UUID.randomUUID().hashCode());
        List<Integer> stats = new ArrayList<>();
        for(int i=0; i<5;i++) {
            stats.add(2);
        }
        shipCrew.setStats(stats);

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.upgradeCrew(s, shipCrew, CrewStat.WEAPON);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try upgrading crew thats already highest level
     */
    @Test
    public void upgradeCrewAlreadyFull() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        Crew shipCrew = new Crew();
        shipCrew.setId(UUID.randomUUID().hashCode());
        List<Integer> stats = new ArrayList<>();
        for(int i=0; i<5;i++) {
            stats.add(10);
        }
        shipCrew.setStats(stats);
        try {
            crewDAO.persist(shipCrew);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        tiles.get(0).setStandingOnMe(shipCrew);
        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(199);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.upgradeCrew(s, shipCrew, CrewStat.WEAPON);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try upgrading crew without money
     */
    @Test
    public void upgradeCrewNoMoney() {
        Ship s = new Ship();
        List<Room> rooms = new ArrayList<>();

        Crew shipCrew = new Crew();
        shipCrew.setId(UUID.randomUUID().hashCode());
        List<Integer> stats = new ArrayList<>();
        for(int i=0; i<5;i++) {
            stats.add(2);
        }
        shipCrew.setStats(stats);
        try {
            crewDAO.persist(shipCrew);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        tiles.get(0).setStandingOnMe(shipCrew);
        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        s.setSystems(rooms);
        s.setId(UUID.randomUUID().hashCode());
        s.setInventory(new ArrayList<Weapon>());
        s.setCoins(0);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.upgradeCrew(s, shipCrew, CrewStat.WEAPON);
        Assert.assertFalse(response.isValidRequest());
    }
}
