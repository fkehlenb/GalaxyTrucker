package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;

public class InventoryUI {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * Inventory background texture
     */
    private Texture inventoryBackground;

    /**
     * Inventory slots
     */
    private List<InventorySlotUI> slots;

    /**
     * Close inventory button
     */
    private ImageButton closeInventoryButton;

    /**
     * Close inventory button texture
     */
    private Texture closeInventoryButtonTexture;

    /**
     * the ship
     */
    private Ship ship;

    /**
     * setup called after initialisation
     *
     * here the inventory slots are initialised for fuel, missiles, crew, weapons, and money
     */
    private void setup() {
    }

    /**
     * show the inventory
     */
    public void showInventoryUI() {
    }

    /**
     * hide the inventory
     */
    public void hideInventoryUI() {
    }

    /**
     * Dispose of inventory
     */
    public void disposeInventoryUI() {
    }

    public Ship getShip() {
        return null;
    }

    public void setShip(Ship ship) {

    }

    /**
     * Constructor
     *
     * @param main - main class
     * @param ship - the ship whose inventory is to be displayed
     */
    public InventoryUI(Main main, Ship ship) {
    }
}
