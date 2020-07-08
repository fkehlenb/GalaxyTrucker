package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;

public class InventoryWeaponSlotUI extends InventorySlotUI {

    /**
     * the texture for the weapon
     */
    private Texture weaponTexture;

    /** Weapon damage */
    private int damage;

    /** Weapon coolDown */
    private int cooldown;

    /** Weapon energy */
    private int energy;

    private int missileCost;
    private float dropchance;
    private int crewdamage;
    /**
     * How many projectiles are fired per burst
     */
    private int burst;
    private float precision;

    /**
     * constructor
     * @param main the main class
     * @param weapon the weapon to be displayed
     */
    public InventoryWeaponSlotUI(Main main, Weapon weapon, float x, float y) {
        super(main, x, y);
        damage = weapon.getDamage();
        cooldown = weapon.getCooldown();
        energy = weapon.getEnergy();
        missileCost = weapon.getMissileCost();
        dropchance = weapon.getDropChance();
        crewdamage = weapon.getCrewDamage();
        burst = weapon.getBurst();
        precision = weapon.getAccuracy();

        weaponTexture = new Texture("shipsys/weapon/laser.png");
    }

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {
        super.disposeInventorySlotUI();
        weaponTexture.dispose();
    }

    public void render() {
        super.render();
        main.batch.begin();
        main.batch.draw(weaponTexture, posX, posY, 22, 67);
        main.batch.end();
    }

    /**
     * show the ui
     */
    @Override
    public void showInventorySlotUI() {

    }

    /**
     * Hide inventory slot ui
     */
    @Override
    public void hideInventorySlotUI() {

    }

    /**
     * Setup called after initialisation
     */
    private void setup()
    {

    }
}
