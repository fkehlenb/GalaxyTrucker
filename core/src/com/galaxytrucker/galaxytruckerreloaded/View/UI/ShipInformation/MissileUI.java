package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * ui for displaying the amount of missiles
 */
public class MissileUI {


    /**
     * Missile UI background
     */
    private Texture missileBackground;

    /**
     * the amount of missiles
     */
    private int amount;

    /**
     * the main class with the sprite batch
     */
    private Main main;

    /**
     * font for displaying text
     */
    private BitmapFont font;

    /**
     * glyph layout for positioning text
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * Constructor
     *
     * @param main - main class used for sprite batch and camera
     * @param missiles the amount of missiles
     */
    public MissileUI(Main main, int missiles, BitmapFont font) {
        this.main = main;
        this.amount = missiles;
        this.font = font;

        missileBackground = new Texture("gameuis/top_missile.png");

        glyph.setText(font, Integer.toString(amount));
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(missileBackground, 600, Main.HEIGHT - 138, 147, 60);
        font.draw(main.batch, glyph, 600 + (147f/2) - glyph.width/2, (Main.HEIGHT - 88) - glyph.height/2);
        main.batch.end();
    }

    /**
     * Dispose of ui
     */
    public void disposeMissileUI() {
        missileBackground.dispose();
    }

    /**
     * the amount of missiles is updated
     * @param amount the new total amount
     */
    public void changeOverallAmount(int amount) {
        this.amount = amount;
        glyph.setText(font, Integer.toString(this.amount));
    }
}
