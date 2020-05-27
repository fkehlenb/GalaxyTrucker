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
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {

    }

    /**
     * Setup called after initialisation
     */
    private void setup()
    {

    }

    /**
     * constructor
     * @param main the main class
     * @param weapon the weapon to be displayed
     */
    public InventoryWeaponSlotUI(Main main, Weapon weapon) {
        super(main);
    }
}
