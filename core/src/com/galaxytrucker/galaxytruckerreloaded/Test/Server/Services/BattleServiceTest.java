package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.PreviousRoundAction;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.BattleService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BattleServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private ShipDAO shipDAO = new ShipDAO();

    private UserDAO userDAO = new UserDAO();

    private OverworldDAO overworldDAO = new OverworldDAO();

    private CrewDAO crewDAO = new CrewDAO();

    private BattleServiceDAO battleServiceDAO = new BattleServiceDAO();

    private RequestObjectDAO requestObjectDAO = new RequestObjectDAO();

    /**
     * add a request to the queue
     */
    @Test
    public void addToQueueSuccess() {
        RequestObject request = new RequestObject();
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        request.setShip(s);

        BattleService service = new BattleService();
        service.setCurrentRound(s.getId());
        service.setRoundActions(new ArrayList<RequestObject>());

        ResponseObject response = service.addToQueue(request);
        Assert.assertTrue(response.isValidRequest());
        Assert.assertTrue(service.getRoundActions().contains(request));
    }

    /**
     * try adding when it is not your round
     */
    @Test
    public void addToQueueNotRound() {
        RequestObject request = new RequestObject();
        Ship s = new Ship();
        s.setId(2);
        request.setShip(s);

        BattleService service = new BattleService();
        service.setCurrentRound(1);
        service.setRoundActions(new ArrayList<RequestObject>());

        ResponseObject response = service.addToQueue(request);
        Assert.assertFalse(response.isValidRequest());
        Assert.assertFalse(service.getRoundActions().contains(request));

        Ship s1 = new Ship();
        s1.setId(1);
        RequestObject request1 = new RequestObject();
        request1.setShip(s1);

        ResponseObject response1 = service.addToQueue(request1);
        Assert.assertTrue(response1.isValidRequest());
        Assert.assertTrue(service.getRoundActions().contains(request1));
    }

    /**
     * play queued moves
     */
    @Test
    public void playMovesSuccess() {
        User u = new User();
        u.setUsername("testuser");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setDifficulty(1);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch (Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser");
        s.setMissiles(5);
        List<Room> rooms = new ArrayList<>();
        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.WEAPON_SYSTEM);
        system.setSystem(true);
        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        w.setWeaponType(WeaponType.BOMB);
        w.setMissileCost(2);
        w.setBurst(2);
        List<Weapon> ws = new ArrayList<>();
        ws.add(w);
        system.setShipWeapons(ws);
        system.setUnlocked(true);
        system.setEnergy(3);
        system.setMaxEnergy(3);
        system.setCrew(new ArrayList<Crew>());
        rooms.add(system);
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        List<Ship> combatans = new ArrayList<>();
        combatans.add(s);
        service.setCurrentRound(s.getId());
        service.setPreviousRoundActions(new ArrayList<PreviousRoundAction>());
        service.setCombatOver(false);
        List<RequestObject> rounds = new ArrayList<>();
        RequestObject round1 = new RequestObject();
        round1.setRequestType(RequestType.ATTACK_SHIP);
        Ship opponent = new Ship();
        opponent.setId(UUID.randomUUID().hashCode());
        opponent.setAssociatedUser("[ENEMY]");
        List<Room> rooms1 = new ArrayList<>();
        Room r = new Room();
        r.setId(UUID.randomUUID().hashCode());
        rooms1.add(r);
        opponent.setSystems(rooms1);
        try {
            shipDAO.persist(opponent);
        }
        catch(Exception e) {
            Assert.fail();
        }
        combatans.add(opponent);
        round1.setShip(s);
        round1.setOpponentShip(opponent);
        round1.setWeapon(w);
        round1.setRoom(r);
        try {
            requestObjectDAO.persist(round1);
        }
        catch(Exception e) {
            Assert.fail();
        }
        rounds.add(round1);
        service.setRoundActions(rounds);
        service.setCombatants(combatans);
        try {
            battleServiceDAO.persist(service);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.playMoves(s);
        Assert.assertTrue(response.isValidRequest());
    }

    /**
     * try fighting with non existing ship
     */
    @Test
    public void playMovesSNotExisting() {
        User u = new User();
        u.setUsername("testuser1");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setDifficulty(1);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch (Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser1");
        s.setMissiles(5);
        List<Room> rooms = new ArrayList<>();
        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.WEAPON_SYSTEM);
        system.setSystem(true);
        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        w.setWeaponType(WeaponType.BOMB);
        w.setMissileCost(2);
        w.setBurst(2);
        List<Weapon> ws = new ArrayList<>();
        ws.add(w);
        system.setShipWeapons(ws);
        system.setUnlocked(true);
        system.setEnergy(3);
        system.setMaxEnergy(3);
        system.setCrew(new ArrayList<Crew>());
        rooms.add(system);
        s.setSystems(rooms);

        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        List<Ship> combatans = new ArrayList<>();
        service.setCurrentRound(s.getId());
        service.setPreviousRoundActions(new ArrayList<PreviousRoundAction>());
        service.setCombatOver(false);
        List<RequestObject> rounds = new ArrayList<>();
        RequestObject round1 = new RequestObject();
        round1.setRequestType(RequestType.ATTACK_SHIP);
        Ship opponent = new Ship();
        opponent.setId(UUID.randomUUID().hashCode());
        opponent.setAssociatedUser("[ENEMY]");
        List<Room> rooms1 = new ArrayList<>();
        Room r = new Room();
        r.setId(UUID.randomUUID().hashCode());
        rooms1.add(r);
        opponent.setSystems(rooms1);
        try {
            shipDAO.persist(opponent);
        }
        catch(Exception e) {
            Assert.fail();
        }
        combatans.add(opponent);
        round1.setOpponentShip(opponent);
        round1.setWeapon(w);
        round1.setRoom(r);
        service.setRoundActions(rounds);
        service.setCombatants(combatans);
        try {
            battleServiceDAO.persist(service);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.playMoves(s);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try attacking without missiles
     */
    @Test
    public void playMovesNoMissiles() {
        User u = new User();
        u.setUsername("testuser3");
        Overworld o = new Overworld();
        o.setId(UUID.randomUUID().hashCode());
        o.setDifficulty(1);
        u.setOverworld(o);
        try {
            overworldDAO.persist(o);
            userDAO.persist(u);
        }
        catch (Exception e) {
            Assert.fail();
        }

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setAssociatedUser("testuser3");
        s.setMissiles(0);
        List<Room> rooms = new ArrayList<>();
        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.WEAPON_SYSTEM);
        system.setSystem(true);
        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        w.setWeaponType(WeaponType.BOMB);
        w.setMissileCost(2);
        w.setBurst(2);
        List<Weapon> ws = new ArrayList<>();
        ws.add(w);
        system.setShipWeapons(ws);
        system.setUnlocked(true);
        system.setEnergy(3);
        system.setMaxEnergy(3);
        system.setCrew(new ArrayList<Crew>());
        rooms.add(system);
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        List<Ship> combatans = new ArrayList<>();
        combatans.add(s);
        service.setCurrentRound(s.getId());
        service.setPreviousRoundActions(new ArrayList<PreviousRoundAction>());
        service.setCombatOver(false);
        List<RequestObject> rounds = new ArrayList<>();
        RequestObject round1 = new RequestObject();
        round1.setRequestType(RequestType.ATTACK_SHIP);
        Ship opponent = new Ship();
        opponent.setId(UUID.randomUUID().hashCode());
        opponent.setAssociatedUser("[ENEMY]");
        List<Room> rooms1 = new ArrayList<>();
        Room r = new Room();
        r.setId(UUID.randomUUID().hashCode());
        rooms1.add(r);
        opponent.setSystems(rooms1);
        try {
            shipDAO.persist(opponent);
        }
        catch(Exception e) {
            Assert.fail();
        }
        combatans.add(opponent);
        round1.setShip(s);
        round1.setOpponentShip(opponent);
        round1.setWeapon(w);
        round1.setRoom(r);
        try {
            requestObjectDAO.persist(round1);
        }
        catch(Exception e) {
            Assert.fail();
        }
        rounds.add(round1);
        service.setRoundActions(rounds);
        service.setCombatants(combatans);
        try {
            battleServiceDAO.persist(service);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.playMoves(s);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * get the updated data
     */
    @Test
    public void getUpdatedDataSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        service.setCombatOver(true);
        List<Ship> com = new ArrayList<>();
        com.add(s);
        service.setCombatants(com);
        try {
            battleServiceDAO.persist(service);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.getUpdatedData(s);
        Assert.assertTrue(response.isValidRequest());
        Assert.assertTrue(response.isCombatOver());
        Assert.assertFalse(response.isCombatWon());
        entityManager.getTransaction().begin();
        BattleService service1 = entityManager.find(BattleService.class, service.getId());
        entityManager.getTransaction().commit();
        Assert.assertTrue(service1.getCombatants().isEmpty());
    }

    /**
     * flee a fight
     */
    @Test
    public void fleeFightSuccess() {

    }
}