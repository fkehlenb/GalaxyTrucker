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
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.InventoryCloseButton;

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
     * button to close inventory
     */
    private InventoryCloseButton closeButton;

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


    /**
     * Constructor
     *
     * @param main - main class
     * @param crew the crew members
     * @param weapons the weapons
     * @param integerValues the integer values to be displayed for a ship
     */
    public InventoryUI(Main main, List<Crew> crew, List<Weapon> weapons, List<Integer> integerValues) {
    }
}
