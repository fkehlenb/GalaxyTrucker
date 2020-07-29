package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * ui for displaying the amount of fuel
 */
public class FuelUI {

    /**
     * fuel UI background
     */
    private Texture fuelBackground;

    /**
     * the amount of fuel
     */
    private int amount = 0;

    /**
     * the main class with the sprite batch
     */
    private Main main;

    /**
     * font for displaying text
     */
    private BitmapFont font;

    /**
     * glyph for correctly centering text
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * Constructor
     *
     * @param main - main class used for sprite batch and camera
     * @param fuel the amount of fuel
     * @param font font for text
     */
    public FuelUI(Main main, int fuel, BitmapFont font) {
        this.main = main;
        this.amount = fuel;
        this.font = font;

        fuelBackground = new Texture("gameuis/top_fuel2.png");

        glyph.setText(font, Integer.toString(amount));
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(fuelBackground, 600, Main.HEIGHT - 183, 147, 60); //238
        font.draw(main.batch, glyph, 600 + (147f/2) - glyph.width/2, (Main.HEIGHT - 133) - glyph.height/2);
        main.batch.end();
    }

    /**
     * Dispose of ui
     */
    public void disposeFuelUI() {
        fuelBackground.dispose();
    }

    /**
     * the amount of fuel is updated
     * @param amount by how much the amount is changed
     */
    public void changeAmount(int amount) {
        this.amount += amount;
        glyph.setText(font, Integer.toString(this.amount));
    }

    /**
     * the amount of fuel is updated
     * @param amount the new total amount
     */
    public void changeOverallAmount(int amount) {
        this.amount = amount;
        glyph.setText(font, Integer.toString(this.amount));
    }
}
