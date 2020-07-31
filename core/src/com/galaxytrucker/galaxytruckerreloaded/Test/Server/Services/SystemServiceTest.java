package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.SystemService;
import org.junit.Assert;
import org.junit.Test;
import sun.jvm.hotspot.memory.SystemDictionary;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SystemServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private SystemService service = new SystemService();

    private ShipDAO shipDAO = new ShipDAO();

    /**
     * try adding energy to cockpit
     */
    @Test
    public void addEnergyCockpit() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.COCKPIT);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.addEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * add energy to system successfully
     */
    @Test
    public void addEnergySuccessful() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.WEAPON_SYSTEM);
        system.setEnergy(0);
        system.setMaxEnergy(10);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setEnergy(10);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.addEnergy(s, system, 1);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        System sys1 = entityManager.find(System.class, system.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(9, s1.getEnergy());
        Assert.assertEquals(1, sys1.getEnergy());
        Assert.assertFalse(sys1.isDisabled());
    }

    /**
     * add energy to system successfully
     */
    @Test
    public void addEnergyNotEnough() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.WEAPON_SYSTEM);
        system.setEnergy(0);
        system.setMaxEnergy(10);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setEnergy(0);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.addEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * add energy to system successfully
     */
    @Test
    public void addEnergyAlreadyFull() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.WEAPON_SYSTEM);
        system.setEnergy(10);
        system.setMaxEnergy(10);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setEnergy(10);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.addEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try adding energy to cameras
     */
    @Test
    public void addEnergyCameras() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.CAMERAS);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.addEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try adding energy when ship not know to server
     */
    @Test
    public void addEnergyNotExisting() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setSystem(true);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.addEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try adding energy when system not unlocked
     */
    @Test
    public void addEnergyNotUnlocked() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setUnlocked(false);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.addEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try removing energy from cockpit
     */
    @Test
    public void removeEnergyCockpit() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.COCKPIT);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.removeEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try removing energy from cameras
     */
    @Test
    public void removeEnergyCameras() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.CAMERAS);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.removeEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try removing energy from system when ship not known
     */
    @Test
    public void removeEnergyNotExisting() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setSystem(true);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.removeEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * remove energy from system that is not unlocked
     */
    @Test
    public void removeEnergyNotUnlocked() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setUnlocked(false);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.removeEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * add energy to system successfully
     */
    @Test
    public void removeEnergySuccessful() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.WEAPON_SYSTEM);
        system.setEnergy(1);
        system.setMaxEnergy(10);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setEnergy(10);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.removeEnergy(s, system, 1);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        System sys1 = entityManager.find(System.class, system.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(11, s1.getEnergy());
        Assert.assertEquals(0, sys1.getEnergy());
        Assert.assertTrue(sys1.isDisabled());
    }

    /**
     * add energy to system successfully
     */
    @Test
    public void removeEnergyAlreadyEmpty() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.WEAPON_SYSTEM);
        system.setEnergy(0);
        system.setMaxEnergy(10);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setEnergy(10);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.removeEnergy(s, system, 1);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * upgrade system in ship that does not exist
     */
    @Test
    public void upgradeSystemNotExisting() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);

        ResponseObject response = service.upgradeSystem(s, system);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * upgrade system that does not exist
     */
    @Test
    public void upgradeSystemSystemNotExisting() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setSystem(true);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.upgradeSystem(s, system);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * upgrade system successfully
     */
    @Test
    public void upgradeSystemSuccessful() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setMaxEnergy(1);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setCoins(100);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.upgradeSystem(s, system);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        System sys1 = entityManager.find(System.class, system.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(2, sys1.getMaxEnergy());
        Assert.assertEquals(85, s1.getCoins());
    }

    /**
     * upgrade system without money
     */
    @Test
    public void upgradeSystemNoMoney() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setMaxEnergy(1);
        system.setSystem(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setCoins(0);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.upgradeSystem(s, system);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * install system in non existing ship
     */
    @Test
    public void installSystemNotExisting() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setSystem(true);
        system.setUnlocked(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);

        ResponseObject response = service.installSystem(s, SystemType.ENGINE);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * install system that is already there
     */
    @Test
    public void installSystemAlreadyThere() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setSystem(true);
        system.setUnlocked(true);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.installSystem(s, SystemType.ENGINE);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * successful sys install
     */
    @Test
    public void installSuccess() {
        List<Room> rooms = new ArrayList<>();

        System system = new System();
        system.setId(UUID.randomUUID().hashCode());
        system.setSystemType(SystemType.ENGINE);
        system.setSystem(true);
        system.setUnlocked(false);
        rooms.add(system);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.installSystem(s, SystemType.ENGINE);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        System sys1 = entityManager.find(System.class, system.getId());
        entityManager.getTransaction().commit();
        Assert.assertTrue(sys1.isUnlocked());
        Assert.assertTrue(sys1.isDisabled());
    }
}
