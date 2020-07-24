package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;

import java.util.List;

/** Includes all content shown on a single event page */
public class EventPage {

    /**
     * Icons to draw (such as rewards)
     */
    private List<Texture> drawables;

    /**
     * the text to display on this event page
     */
    private String text;

    /**
     * the coordinates where drawing should begin
     */
    private float x, y;

    /**
     * the main game class
     */
    private Main main;

    /**
     * font used to draw text
     */
    private BitmapFont font15;

    /**
     * the glyphlayout needed to properly center the text
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * Constructor
     *
     * @param main - the main class object
     * @param draw the drawables on this page
     * @param text the text displayed on this page
     */
    public EventPage(Main main, List<Texture> draw, String text, float x, float y, BitmapFont font15) {
        drawables = draw;
        this.text = text;
        this.main = main;
        this.x = x;
        this.y = y;
        this.font15 = font15;

        glyph.setText(font15, text);
    }

    /**
     * render
     */
    public void render() {
        main.batch.begin();
        int i = 0;
        for(Texture t : drawables) {
            if (t!=null) {
                main.batch.draw(t, x + i, y + i, 10, 10);
                i += 15;
            }
        }
        font15.draw(main.batch, glyph, x - glyph.width/2, y - glyph.height/2);
        main.batch.end();
    }

    /**
     * Dispose of page
     */
    public void disposePage() {
        for(Texture t : drawables) {
            t.dispose();
        }
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }


    /**
     * Draw on top of event gui
     */
    public void showPage() {
    }

    /**
     * Hide the page
     */
    public void hidePage() {
    }
}
