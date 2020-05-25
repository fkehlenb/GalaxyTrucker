package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public abstract class InventorySlotUI {

    /**
     * Sprite batch for rendering
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * Inventory slot position x
     */
    private float posX;

    /**
     * Inventory slot position y
     */
    private float posY;

    /**
     * Inventory slot background
     */
    private Texture inventorySlotTexture;

    /**
     * show the ui
     */
    public abstract void showInventorySlotUI();

    /**
     * Hide inventory slot ui
     */
    public abstract void hideInventorySlotUI();

    /**
     * Dispose inventory slot ui
     */
    public abstract void disposeInventorySlotUI();

    /**
     * Constructor
     *
     * @param main - main class
     */
    public InventorySlotUI(Main main) {
    }
}
