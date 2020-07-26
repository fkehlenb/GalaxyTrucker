package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.galaxytrucker.galaxytruckerreloaded.Main;

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

    private BitmapFont font;

    private GlyphLayout glyph = new GlyphLayout();

    /**
     * Constructor
     *
     * @param main - main class used for sprite batch and camera
     * @param fuel the amount of fuel
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
     * setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the ui
     */
    public void showFuelUI() {
    }

    /**
     * hide the ui
     */
    public void hideFuelUI() {
    }

    /**
     * Dispose of ui
     */
    public void disposeFuelUI() {
        fuelBackground.dispose();
        font.dispose();
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
