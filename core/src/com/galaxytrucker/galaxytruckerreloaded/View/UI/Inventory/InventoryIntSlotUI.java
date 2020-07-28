package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * to display int values (fuel etc) in the inventory
 */
public class InventoryIntSlotUI extends InventorySlotUI {

    /**
     * glyph layout for the int value
     */
    private GlyphLayout glyphLayout = new GlyphLayout();

    /**
     * constructor
     * @param main main class extending game
     * @param value int value to be displayed
     * @param x x position
     * @param y y position
     * @param text the text explaining what value this is
     * @param font font for text
     */
    public InventoryIntSlotUI(Main main, int value, float x, float y, String text, BitmapFont font) {
        super(main, x, y, font);

        glyphLayout.setText(font, text + ": " + value);
    }

    /**
     * render
     * without stage stuff
     */
    public void render() {
        main.batch.begin();
        font.draw(main.batch, glyphLayout, posX, posY);
        main.batch.end();
    }

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {
        //nothing to dispose
    }
}
