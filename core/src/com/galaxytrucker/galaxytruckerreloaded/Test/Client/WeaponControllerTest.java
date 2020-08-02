package com.galaxytrucker.galaxytruckerreloaded.Test.Client;

import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.WeaponController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

public class WeaponControllerTest {
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
     * unequip a weapon
     */
    @Test
    public void unequipSuccess() {
        WeaponController controller = WeaponController.getInstance(null);

        Weapon w = new Weapon();
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System)r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                w = ((System) r).getShipWeapons().get(0);
                break;
            }
        }

        boolean success = controller.unequipWeapon(w);
        Assert.assertTrue(success);
        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertTrue(s1.getInventory().contains(w));
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System)r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                Assert.assertTrue(((System)r).getShipWeapons().contains(w));
                break;
            }
        }
    }

    /**
     * try unequipping a weapon already in inventory
     */
    @Test
    public void unequipUnequipped() {
        WeaponController controller = WeaponController.getInstance(null);

        Weapon w = new Weapon();
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System)r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                w = ((System) r).getShipWeapons().get(0);
                break;
            }
        }

        controller.unequipWeapon(w);

        boolean success = controller.unequipWeapon(w);
        Assert.assertFalse(success);
    }


    /**
     * try unequipping unknown weapon
     */
    @Test
    public void unequipStrangeWeapon() {
        WeaponController controller = WeaponController.getInstance(null);

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());
        boolean success = controller.unequipWeapon(w);
        Assert.assertFalse(success);
    }

    /**
     * equip a weapon
     */
    @Test
    public void equipSuccess() {
        WeaponController controller = WeaponController.getInstance(null);

        Weapon w = new Weapon();
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System)r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                w = ((System) r).getShipWeapons().get(0);
                break;
            }
        }

        controller.unequipWeapon(w);

        Ship s1 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Weapon w1 = s1.getInventory().get(0);

        boolean success = controller.equipWeapon(w1);
        Assert.assertTrue(success);
        Ship s2 = ClientControllerCommunicator.getInstance(null).getClientShip();
        Assert.assertFalse(s2.getInventory().contains(w));
        for(Room r : s2.getSystems()) {
            if(r.isSystem() && ((System)r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                Assert.assertTrue(((System)r).getShipWeapons().contains(w));
                break;
            }
        }
    }

    /**
     * try equipping an equipped weapon
     */
    @Test
    public void equipEquipped() {
        WeaponController controller = WeaponController.getInstance(null);

        Weapon w = new Weapon();
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();
        for(Room r : s.getSystems()) {
            if(r.isSystem() && ((System)r).getSystemType() == SystemType.WEAPON_SYSTEM) {
                w = ((System) r).getShipWeapons().get(0);
                break;
            }
        }

        boolean success = controller.equipWeapon(w);
        Assert.assertFalse(success);
    }

    /**
     * try equipping strange weapon
     */
    @Test
    public void equipStrangeWeapon() {
        WeaponController controller = WeaponController.getInstance(null);

        Weapon w = new Weapon();
        w.setId(UUID.randomUUID().hashCode());

        boolean success = controller.equipWeapon(w);
        Assert.assertFalse(success);
    }
}
