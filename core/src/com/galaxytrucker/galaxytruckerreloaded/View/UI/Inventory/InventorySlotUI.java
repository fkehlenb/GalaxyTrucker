package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public abstract class InventorySlotUI {

    /**
     * Inventory slot position x
     */
    protected float posX;

    /**
     * Inventory slot position y
     */
    protected float posY;

    /**
     * Inventory slot background
     */
    private Texture inventorySlotTexture;

    protected Main main;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public InventorySlotUI(Main main, float x, float y) {
        this.main = main;

        posX = x;
        posY = y;

        //inventorySlotTexture = new Texture();
    }

    /**
     * render
     * without stage stuff
     */
    public void render() {
        /*main.batch.begin();
        main.batch.draw(inventorySlotTexture, posX, posY, 0, 0); //TODO wh
        main.batch.end();*/
    }

    /**
     * Dispose inventory slot ui
     */
    public void disposeInventorySlotUI() {
        //inventorySlotTexture.dispose();
    }

    /**
     * show the ui
     */
    public abstract void showInventorySlotUI();

    /**
     * Hide inventory slot ui
     */
    public abstract void hideInventorySlotUI();

}
