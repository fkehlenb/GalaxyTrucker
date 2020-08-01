package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.TravelService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TravelServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private TravelService service = new TravelService();

    private ShipDAO shipDAO = new ShipDAO();

    private PlanetDAO planetDAO = new PlanetDAO();

    private CrewDAO crewDAO = new CrewDAO();

    private UserDAO userDAO = new UserDAO();

    private OverworldDAO overworldDAO = new OverworldDAO();

    /**
     * try jumping successfully
     */
    @Test
    public void jumpSuccess() {
        Planet sp = new Planet();
        sp.setId(UUID.randomUUID().hashCode());
        sp.setPosX(1);
        sp.setPosY(1);
        sp.setEvent(PlanetEvent.METEORSHOWER);

        Planet dp = new Planet();
        dp.setId(UUID.randomUUID().hashCode());
        dp.setPosX(2);
        dp.setPosY(2);
        dp.setEvent(PlanetEvent.METEORSHOWER);

        Planet startp = new Planet();
        startp.setId(UUID.randomUUID().hashCode());

        Planet bossp = new Planet();
        bossp.setId(UUID.randomUUID().hashCode());
        try {
            planetDAO.persist(dp);
            planetDAO.persist(sp);
            planetDAO.persist(startp);
            planetDAO.persist(bossp);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Room> rooms = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System engine = new System();
        engine.setId(UUID.randomUUID().hashCode());
        engine.setTiles(tiles);
        engine.setEnergy(2);
        engine.setMaxEnergy(3);
        engine.setDisabled(false);
        engine.setSystem(true);
        engine.setSystemType(SystemType.ENGINE);
        rooms.add(engine);

        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System cockpit = new System();
        cockpit.setId(UUID.randomUUID().hashCode());
        cockpit.setTiles(tiles1);
        cockpit.setEnergy(1);
        cockpit.setMaxEnergy(3);
        cockpit.setDisabled(false);
        cockpit.setSystem(true);
        cockpit.setSystemType(SystemType.COCKPIT);
        rooms.add(cockpit);


        User u = new User();
        u.setUsername("testuser");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setStartPlanet(startp);
        o.setBossPlanet(bossp);

        List<Planet> planets = new ArrayList<>();
        planets.add(sp);
        planets.add(dp);
        o.setPlanetMap(planets);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser");
        s.setPlanet(sp);
        s.setFTLCharge(100);
        s.setFuel(10);
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.jump(s, dp);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Planet p1 = entityManager.find(Planet.class, dp.getId());
        Planet p2 = entityManager.find(Planet.class, sp.getId());
        entityManager.getTransaction().commit();

        Assert.assertEquals(9, s1.getFuel());
        Assert.assertTrue(p1.getShips().contains(s1));
        Assert.assertFalse(p2.getShips().contains(s1));
    }

    public void jumpNoFuel() {
        Planet sp = new Planet();
        sp.setId(UUID.randomUUID().hashCode());
        sp.setPosX(1);
        sp.setPosY(1);
        sp.setEvent(PlanetEvent.METEORSHOWER);

        Planet dp = new Planet();
        dp.setId(UUID.randomUUID().hashCode());
        dp.setPosX(2);
        dp.setPosY(2);
        dp.setEvent(PlanetEvent.METEORSHOWER);

        Planet startp = new Planet();
        startp.setId(UUID.randomUUID().hashCode());

        Planet bossp = new Planet();
        bossp.setId(UUID.randomUUID().hashCode());
        try {
            planetDAO.persist(dp);
            planetDAO.persist(sp);
            planetDAO.persist(startp);
            planetDAO.persist(bossp);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Room> rooms = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System engine = new System();
        engine.setId(UUID.randomUUID().hashCode());
        engine.setTiles(tiles);
        engine.setEnergy(2);
        engine.setMaxEnergy(3);
        engine.setDisabled(false);
        engine.setSystem(true);
        engine.setSystemType(SystemType.ENGINE);
        rooms.add(engine);

        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System cockpit = new System();
        cockpit.setId(UUID.randomUUID().hashCode());
        cockpit.setTiles(tiles1);
        cockpit.setEnergy(1);
        cockpit.setMaxEnergy(3);
        cockpit.setDisabled(false);
        cockpit.setSystem(true);
        cockpit.setSystemType(SystemType.COCKPIT);
        rooms.add(cockpit);


        User u = new User();
        u.setUsername("testuser1");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setStartPlanet(startp);
        o.setBossPlanet(bossp);

        List<Planet> planets = new ArrayList<>();
        planets.add(sp);
        planets.add(dp);
        o.setPlanetMap(planets);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser1");
        s.setPlanet(sp);
        s.setFTLCharge(100);
        s.setFuel(0);
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.jump(s, dp);
        Assert.assertFalse(response.isValidRequest());
    }

    public void jumpNoFTL() {
        Planet sp = new Planet();
        sp.setId(UUID.randomUUID().hashCode());
        sp.setPosX(1);
        sp.setPosY(1);
        sp.setEvent(PlanetEvent.METEORSHOWER);

        Planet dp = new Planet();
        dp.setId(UUID.randomUUID().hashCode());
        dp.setPosX(2);
        dp.setPosY(2);
        dp.setEvent(PlanetEvent.METEORSHOWER);

        Planet startp = new Planet();
        startp.setId(UUID.randomUUID().hashCode());

        Planet bossp = new Planet();
        bossp.setId(UUID.randomUUID().hashCode());
        try {
            planetDAO.persist(dp);
            planetDAO.persist(sp);
            planetDAO.persist(startp);
            planetDAO.persist(bossp);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Room> rooms = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System engine = new System();
        engine.setId(UUID.randomUUID().hashCode());
        engine.setTiles(tiles);
        engine.setEnergy(2);
        engine.setMaxEnergy(3);
        engine.setDisabled(false);
        engine.setSystem(true);
        engine.setSystemType(SystemType.ENGINE);
        rooms.add(engine);

        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System cockpit = new System();
        cockpit.setId(UUID.randomUUID().hashCode());
        cockpit.setTiles(tiles1);
        cockpit.setEnergy(1);
        cockpit.setMaxEnergy(3);
        cockpit.setDisabled(false);
        cockpit.setSystem(true);
        cockpit.setSystemType(SystemType.COCKPIT);
        rooms.add(cockpit);


        User u = new User();
        u.setUsername("testuser2");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setStartPlanet(startp);
        o.setBossPlanet(bossp);

        List<Planet> planets = new ArrayList<>();
        planets.add(sp);
        planets.add(dp);
        o.setPlanetMap(planets);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser2");
        s.setPlanet(sp);
        s.setFTLCharge(0);
        s.setFuel(10);
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.jump(s, dp);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try jumping to same planet
     */
    @Test
    public void jumpSamePlanet() {
        Planet sp = new Planet();
        sp.setId(UUID.randomUUID().hashCode());
        sp.setPosX(1);
        sp.setPosY(1);
        sp.setEvent(PlanetEvent.METEORSHOWER);

        Planet startp = new Planet();
        startp.setId(UUID.randomUUID().hashCode());

        Planet bossp = new Planet();
        bossp.setId(UUID.randomUUID().hashCode());
        try {
            planetDAO.persist(sp);
            planetDAO.persist(startp);
            planetDAO.persist(bossp);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Room> rooms = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System engine = new System();
        engine.setId(UUID.randomUUID().hashCode());
        engine.setTiles(tiles);
        engine.setEnergy(2);
        engine.setMaxEnergy(3);
        engine.setDisabled(false);
        engine.setSystem(true);
        engine.setSystemType(SystemType.ENGINE);
        rooms.add(engine);

        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System cockpit = new System();
        cockpit.setId(UUID.randomUUID().hashCode());
        cockpit.setTiles(tiles1);
        cockpit.setEnergy(1);
        cockpit.setMaxEnergy(3);
        cockpit.setDisabled(false);
        cockpit.setSystem(true);
        cockpit.setSystemType(SystemType.COCKPIT);
        rooms.add(cockpit);


        User u = new User();
        u.setUsername("testuser7");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setStartPlanet(startp);
        o.setBossPlanet(bossp);

        List<Planet> planets = new ArrayList<>();
        planets.add(sp);
        o.setPlanetMap(planets);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser7");
        s.setPlanet(sp);
        s.setFTLCharge(100);
        s.setFuel(10);
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.jump(s, sp);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try jumping with non existing planet
     */
    @Test
    public void jumpSNotExisting() {
        Planet sp = new Planet();
        sp.setId(UUID.randomUUID().hashCode());
        sp.setPosX(1);
        sp.setPosY(1);
        sp.setEvent(PlanetEvent.METEORSHOWER);

        Planet dp = new Planet();
        dp.setId(UUID.randomUUID().hashCode());
        dp.setPosX(2);
        dp.setPosY(2);
        dp.setEvent(PlanetEvent.METEORSHOWER);

        Planet startp = new Planet();
        startp.setId(UUID.randomUUID().hashCode());

        Planet bossp = new Planet();
        bossp.setId(UUID.randomUUID().hashCode());
        try {
            planetDAO.persist(dp);
            planetDAO.persist(sp);
            planetDAO.persist(startp);
            planetDAO.persist(bossp);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Room> rooms = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System engine = new System();
        engine.setId(UUID.randomUUID().hashCode());
        engine.setTiles(tiles);
        engine.setEnergy(2);
        engine.setMaxEnergy(3);
        engine.setDisabled(false);
        engine.setSystem(true);
        engine.setSystemType(SystemType.ENGINE);
        rooms.add(engine);

        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System cockpit = new System();
        cockpit.setId(UUID.randomUUID().hashCode());
        cockpit.setTiles(tiles1);
        cockpit.setEnergy(1);
        cockpit.setMaxEnergy(3);
        cockpit.setDisabled(false);
        cockpit.setSystem(true);
        cockpit.setSystemType(SystemType.COCKPIT);
        rooms.add(cockpit);


        User u = new User();
        u.setUsername("testuser3");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setStartPlanet(startp);
        o.setBossPlanet(bossp);

        List<Planet> planets = new ArrayList<>();
        planets.add(sp);
        planets.add(dp);
        o.setPlanetMap(planets);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser3");
        s.setPlanet(sp);
        s.setFTLCharge(100);
        s.setFuel(10);
        s.setSystems(rooms);

        ResponseObject response = service.jump(s, dp);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try jumping to non existing planet
     */
    @Test
    public void jumpDestinyNotExisting() {
        Planet sp = new Planet();
        sp.setId(UUID.randomUUID().hashCode());
        sp.setPosX(1);
        sp.setPosY(1);
        sp.setEvent(PlanetEvent.METEORSHOWER);

        Planet dp = new Planet();
        dp.setId(UUID.randomUUID().hashCode());
        dp.setPosX(2);
        dp.setPosY(2);
        dp.setEvent(PlanetEvent.METEORSHOWER);

        Planet startp = new Planet();
        startp.setId(UUID.randomUUID().hashCode());

        Planet bossp = new Planet();
        bossp.setId(UUID.randomUUID().hashCode());
        try {
            planetDAO.persist(sp);
            planetDAO.persist(startp);
            planetDAO.persist(bossp);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Room> rooms = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System engine = new System();
        engine.setId(UUID.randomUUID().hashCode());
        engine.setTiles(tiles);
        engine.setEnergy(2);
        engine.setMaxEnergy(3);
        engine.setDisabled(false);
        engine.setSystem(true);
        engine.setSystemType(SystemType.ENGINE);
        rooms.add(engine);

        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System cockpit = new System();
        cockpit.setId(UUID.randomUUID().hashCode());
        cockpit.setTiles(tiles1);
        cockpit.setEnergy(1);
        cockpit.setMaxEnergy(3);
        cockpit.setDisabled(false);
        cockpit.setSystem(true);
        cockpit.setSystemType(SystemType.COCKPIT);
        rooms.add(cockpit);


        User u = new User();
        u.setUsername("testuser4");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setStartPlanet(startp);
        o.setBossPlanet(bossp);

        List<Planet> planets = new ArrayList<>();
        planets.add(sp);
        o.setPlanetMap(planets);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser4");
        s.setPlanet(sp);
        s.setFTLCharge(100);
        s.setFuel(10);
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.jump(s, dp);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try jumping from non existing planet
     */
    @Test
    public void jumpStartNotExisting() {
        Planet sp = new Planet();
        sp.setId(UUID.randomUUID().hashCode());
        sp.setPosX(1);
        sp.setPosY(1);
        sp.setEvent(PlanetEvent.METEORSHOWER);

        Planet dp = new Planet();
        dp.setId(UUID.randomUUID().hashCode());
        dp.setPosX(2);
        dp.setPosY(2);
        dp.setEvent(PlanetEvent.METEORSHOWER);

        Planet startp = new Planet();
        startp.setId(UUID.randomUUID().hashCode());

        Planet bossp = new Planet();
        bossp.setId(UUID.randomUUID().hashCode());
        try {
            planetDAO.persist(dp);
            planetDAO.persist(startp);
            planetDAO.persist(bossp);
        }
        catch(Exception e) {
            Assert.fail();
        }

        List<Room> rooms = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System engine = new System();
        engine.setId(UUID.randomUUID().hashCode());
        engine.setTiles(tiles);
        engine.setEnergy(2);
        engine.setMaxEnergy(3);
        engine.setDisabled(false);
        engine.setSystem(true);
        engine.setSystemType(SystemType.ENGINE);
        rooms.add(engine);

        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        System cockpit = new System();
        cockpit.setId(UUID.randomUUID().hashCode());
        cockpit.setTiles(tiles1);
        cockpit.setEnergy(1);
        cockpit.setMaxEnergy(3);
        cockpit.setDisabled(false);
        cockpit.setSystem(true);
        cockpit.setSystemType(SystemType.COCKPIT);
        rooms.add(cockpit);


        User u = new User();
        u.setUsername("testuser5");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setStartPlanet(startp);
        o.setBossPlanet(bossp);

        List<Planet> planets = new ArrayList<>();
        planets.add(dp);
        o.setPlanetMap(planets);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser5");
        s.setFTLCharge(100);
        s.setFuel(10);
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.jump(s, dp);
        Assert.assertFalse(response.isValidRequest());
    }
}
