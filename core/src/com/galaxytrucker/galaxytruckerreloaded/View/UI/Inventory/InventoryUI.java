package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;

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
     * setup called after initialisation
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
     */
    public InventoryUI(Main main) {
    }
}
