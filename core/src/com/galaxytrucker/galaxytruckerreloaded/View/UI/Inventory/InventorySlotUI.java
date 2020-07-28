package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * abstract class for the slot uis
 */
public abstract class InventorySlotUI {

    /**
     * Inventory slot position x
     */
    float posX;

    /**
     * Inventory slot position y
     */
    float posY;

    /**
     * main class extending game
     */
    protected Main main;

    /**
     * font for text
     */
    protected BitmapFont font;


    /**
     * constructor
     * @param main main class extending game
     * @param x x position
     * @param y y position
     * @param font font for text
     */
    public InventorySlotUI(Main main, float x, float y, BitmapFont font) {
        this.main = main;
        this.font = font;

        posX = x;
        posY = y;
    }

    /**
     * render
     * without stage stuff
     */
    public abstract void render();

    /**
     * Dispose inventory slot ui
     */
    public abstract void disposeInventorySlotUI();


}
