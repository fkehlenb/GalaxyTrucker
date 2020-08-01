package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.WeaponService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WeaponServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private WeaponService service = new WeaponService();

    private ShipDAO shipDAO = new ShipDAO();

    private WeaponDAO weaponDAO = new WeaponDAO();

    /**
     * equip a weapon
     */
    @Test
    public void equipSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        List<Room> rooms = new ArrayList<>();
        System weaponsys = new System();
        weaponsys.setId(UUID.randomUUID().hashCode());
        List<Weapon> sysws = new ArrayList<>();
        weaponsys.setSystemType(SystemType.WEAPON_SYSTEM);
        weaponsys.setShipWeapons(sysws);
        weaponsys.setSystem(true);
        rooms.add(weaponsys);

        s.setSystems(rooms);

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();
        inventory.add(w);

        s.setInventory(inventory);

        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.equipWeapon(s, w);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Weapon w1 = entityManager.find(Weapon.class, w.getId());
        entityManager.getTransaction().commit();
        Assert.assertTrue(s1.getInventory().isEmpty());
        Assert.assertTrue(((System)s1.getSystems().get(0)).getShipWeapons().contains(w1));
        Assert.assertEquals(w1.getWeaponSystem().getId(), s1.getSystems().get(0).getId());
    }

    /**
     * try equipping non existing weapon
     */
    @Test
    public void equipWNonExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        List<Room> rooms = new ArrayList<>();
        System weaponsys = new System();
        weaponsys.setId(UUID.randomUUID().hashCode());
        List<Weapon> sysws = new ArrayList<>();
        weaponsys.setSystemType(SystemType.WEAPON_SYSTEM);
        weaponsys.setShipWeapons(sysws);
        weaponsys.setSystem(true);
        rooms.add(weaponsys);

        s.setSystems(rooms);

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();

        s.setInventory(inventory);

        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.equipWeapon(s, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try equipping a weapon to a non existing ship
     */
    @Test
    public void equipSNotExisting() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        List<Room> rooms = new ArrayList<>();
        System weaponsys = new System();
        weaponsys.setId(UUID.randomUUID().hashCode());
        List<Weapon> sysws = new ArrayList<>();
        weaponsys.setSystemType(SystemType.WEAPON_SYSTEM);
        weaponsys.setShipWeapons(sysws);
        weaponsys.setSystem(true);
        rooms.add(weaponsys);

        s.setSystems(rooms);

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();
        inventory.add(w);

        s.setInventory(inventory);

        ResponseObject response = service.equipWeapon(s, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try equipping weapon without weapon system
     */
    @Test
    public void equipNoSystem() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        List<Room> rooms = new ArrayList<>();
        System weaponsys = new System();
        weaponsys.setId(UUID.randomUUID().hashCode());
        weaponsys.setSystemType(SystemType.ENGINE);
        weaponsys.setSystem(true);
        rooms.add(weaponsys);

        s.setSystems(rooms);

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();
        inventory.add(w);

        s.setInventory(inventory);

        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.equipWeapon(s, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try equipping weapon with no space left
     */
    public void equipNoSpace() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        List<Room> rooms = new ArrayList<>();
        System weaponsys = new System();
        weaponsys.setId(UUID.randomUUID().hashCode());
        List<Weapon> sysws = new ArrayList<>();
        weaponsys.setSystemType(SystemType.WEAPON_SYSTEM);
        weaponsys.setShipWeapons(sysws);
        weaponsys.setSystem(true);
        rooms.add(weaponsys);

        s.setSystems(rooms);

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();
        inventory.add(w);
        for(int i=0;i<3;i++) {
            Weapon ww = new Weapon();
            ww.setId(UUID.randomUUID().hashCode());
            inventory.add(ww);
        }

        s.setInventory(inventory);

        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.equipWeapon(s, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * unequip a weapon
     */
    @Test
    public void unequipSuccess() {

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        List<Room> rooms = new ArrayList<>();
        System weaponsys = new System();
        weaponsys.setId(UUID.randomUUID().hashCode());
        List<Weapon> sysws = new ArrayList<>();
        sysws.add(w);
        weaponsys.setSystemType(SystemType.WEAPON_SYSTEM);
        weaponsys.setShipWeapons(sysws);
        weaponsys.setSystem(true);
        rooms.add(weaponsys);

        s.setSystems(rooms);

        s.setInventory(inventory);

        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.unequipWeapon(s, w);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        Weapon w1 = entityManager.find(Weapon.class, w.getId());
        entityManager.getTransaction().commit();
        Assert.assertTrue(s1.getInventory().contains(w1));
        Assert.assertTrue(((System)s1.getSystems().get(0)).getShipWeapons().isEmpty());
        Assert.assertEquals(0, w1.getCooldown());
        Assert.assertNull(w1.getWeaponSystem());
    }

    /**
     * try unequipping a non existing weapon
     */
    @Test
    public void unequipWNotExisting() {
        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        List<Room> rooms = new ArrayList<>();
        System weaponsys = new System();
        weaponsys.setId(UUID.randomUUID().hashCode());
        List<Weapon> sysws = new ArrayList<>();
        weaponsys.setSystemType(SystemType.WEAPON_SYSTEM);
        weaponsys.setShipWeapons(sysws);
        weaponsys.setSystem(true);
        rooms.add(weaponsys);

        s.setSystems(rooms);

        s.setInventory(inventory);

        try {
            shipDAO.persist(s);
        }
        catch(Exception e) {
            Assert.fail();
        }

        ResponseObject response = service.unequipWeapon(s, w);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * try unequipping weapon in non existing ship
     */
    @Test
    public void unequipSNotExisting() {
        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        List<Weapon> inventory = new ArrayList<>();

        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        List<Room> rooms = new ArrayList<>();
        System weaponsys = new System();
        weaponsys.setId(UUID.randomUUID().hashCode());
        List<Weapon> sysws = new ArrayList<>();
        sysws.add(w);
        weaponsys.setSystemType(SystemType.WEAPON_SYSTEM);
        weaponsys.setShipWeapons(sysws);
        weaponsys.setSystem(true);
        rooms.add(weaponsys);

        s.setSystems(rooms);
        s.setInventory(inventory);

        ResponseObject response = service.unequipWeapon(s, w);
        Assert.assertFalse(response.isValidRequest());
    }
}
