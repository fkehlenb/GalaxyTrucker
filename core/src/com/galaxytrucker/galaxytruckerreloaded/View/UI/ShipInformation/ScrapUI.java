package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * ui for displaying scrap
 */
public class ScrapUI {

    /**
     * Scrap UI background
     */
    private Texture scrapBackground;

    /**
     * the amount of money
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
     * @param money the amount of money
     * @param font font for text
     */
    public ScrapUI(Main main, int money, BitmapFont font) {
        this.main = main;
        this.amount = money;
        this.font = font;

        scrapBackground = new Texture("gameuis/top_scrap.png");

        glyph.setText(font, Integer.toString(amount));
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(scrapBackground, 600, Main.HEIGHT - 93, 147, 60);
        font.draw(main.batch, glyph, 600 + (147f/2) - glyph.width/2, (Main.HEIGHT - 43) - glyph.height/2);
        main.batch.end();
    }

    /**
     * Dispose of ui
     */
    public void disposeScrapUI() {
        scrapBackground.dispose();
        font.dispose();
    }

    /**
     * the amount of money is updated
     * @param amount by how much the amount is changed
     */
    public void changeAmount(int amount) {
        this.amount += amount;
        glyph.setText(font, Integer.toString(this.amount));
    }

    /**
     * the amount of money is updated
     * @param amount the new total amount
     */
    public void changeOverallAmount(int amount) {
        this.amount = amount;
        glyph.setText(font, Integer.toString(this.amount));
    }
}
