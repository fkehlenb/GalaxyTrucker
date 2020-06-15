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
     * Constructor
     *
     * @param main - main class
     */
    public InventoryIntSlotUI(Main main, int value, float x, float y, String text) {
        super(main, x, y);
        this.value = value;
        this.text = text;
        //texture = new Texture();
    }

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {
        super.disposeInventorySlotUI();
        //texture.dispose();
    }

    /**
     * render
     * without stage stuff
     */
    public void render() {
        super.render();
        /*main.batch.begin();
        main.batch.draw(texture, 0, 0, 0, 0);//TODO whxy
        main.batch.end();*/
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
}
