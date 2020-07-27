package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;

import java.util.HashMap;

/** Includes all content shown on a single event page */
public class EventPage {

    /**
     * Icons to draw (such as rewards)
     */
    private HashMap<Texture, GlyphLayout> drawables;

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
     * the width and height of the background texture, for better positioning
     */
    private float width, height;

    /**
     * Constructor
     *
     * @param main - the main class object
     * @param draw the drawables on this page
     * @param text the text displayed on this page
     */
    public EventPage(Main main, HashMap<Texture, GlyphLayout> draw, String text, float x, float y, BitmapFont font15, float width, float height) {
        drawables = draw;
        this.text = text;
        this.main = main;
        this.x = x;
        this.y = y;
        this.font15 = font15;
        this.width = width;
        this.height = height;

        glyph.setText(font15, text);
    }

    /**
     * render
     */
    public void render() {
        main.batch.begin();
        int i = 0;
        float iy = y + height - 40 - glyph.height/2 - 80; //40 under the rewards text
        float ix = x + width/2 - 210; //middle minus the stuff
        for(Texture t : drawables.keySet()) {
            main.batch.draw(t, ix + (i*140), iy, t.getWidth(), t.getHeight());
            font15.draw(main.batch, drawables.get(t), ix + (i*140) + (147f/2) - drawables.get(t).width/2, iy + 40 - drawables.get(t).height/2);
            i++;
            i = i % 3;
            if(i == 0) {
                iy -= 80;
            }
        }
        if(text.equals("Rewards")) {
            font15.draw(main.batch, glyph, x + (width / 2) - glyph.width / 2, y + height - 40 - glyph.height / 2);
        }
        else {
            font15.draw(main.batch, glyph, x + (width/2) - glyph.width/2, y + height/2 - glyph.height/2);
        }
        main.batch.end();
    }

    /**
     * Dispose of page
     */
    public void disposePage() {
        for(Texture t : drawables.keySet()) {
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
