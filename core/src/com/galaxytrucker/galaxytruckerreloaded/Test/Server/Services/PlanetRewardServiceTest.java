package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.PlanetRewardService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlanetRewardServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private PlanetRewardService service = new PlanetRewardService();

    private PlanetDAO planetDAO = new PlanetDAO();

    private ShipDAO shipDAO = new ShipDAO();

    /**
     * test whether a planet can be looted multiple times
     */
    @Test
    public void getRewardsAlreadyLooted() {
        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());
        planet.setLooted(true);
        try {
            planetDAO.persist(planet);
        }
        catch(Exception e) {
            Assert.fail();
        }
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.getRewards(s, planet);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * test whether a ship that the server does not know can get items through loot
     */
    @Test
    public void getRewardsUnknownShip() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());


        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());
        try {
            planetDAO.persist(planet);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.getRewards(s, planet);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * test whether a planet the server does not know can be looted
     */
    @Test
    public void getRewardsUnknownPlanet() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());

        ResponseObject response = service.getRewards(s, planet);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * testing get rewards in situation that should be successful
     */
    @Test
    public void getRewardsSuccess() {

        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());
        planet.setEvent(PlanetEvent.METEORSHOWER);
        try {
            planetDAO.persist(planet);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Room> rooms = new ArrayList<>();

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));

        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setInventory(new ArrayList<Weapon>());
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.getRewards(s, planet);
        Assert.assertTrue(response.isValidRequest());
    }
}
