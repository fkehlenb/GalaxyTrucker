package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public class InventoryIntSlotUI extends InventorySlotUI {

    private GlyphLayout glyphLayout = new GlyphLayout();

    /**
     * Constructor
     *
     * @param main - main class
     */
    public InventoryIntSlotUI(Main main, int value, float x, float y, String text) {
        super(main, x, y);

        glyphLayout.setText(font, text + ": " + value);
    }

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {
        super.disposeInventorySlotUI();
        font.dispose();
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
     * render
     * without stage stuff
     */
    public void render() {
        super.render();
        main.batch.begin();
        font.draw(main.batch, glyphLayout, posX, posY);
        main.batch.end();
    }
}
