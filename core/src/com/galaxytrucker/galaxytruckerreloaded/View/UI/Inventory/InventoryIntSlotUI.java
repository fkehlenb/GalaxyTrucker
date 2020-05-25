package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public class InventoryIntSlotUI extends InventorySlotUI {

    /**
     * the value of the thing
     */
    private int value;

    /**
     * the texture
     */
    private Texture texture;

    /**
     * the text explaining what this inventory slot represents
     */
    private String text;

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
     * Constructor
     *
     * @param main - main class
     */
    public InventoryIntSlotUI(Main main, int value) {
        super(main);
    }
}
