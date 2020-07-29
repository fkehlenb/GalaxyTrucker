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
     * constructor
     * @param main main class extending game
     * @param draw hashmap of drawables and the glyphlayouts with the accompanying text, if this page displays rewards
     * @param text the text explaining the planet event
     * @param x x position (lower left corner)
     * @param y y position (lower left corner)
     * @param font15 font, size 15
     * @param width width of the event window
     * @param height height of the event window
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
     * render everything
     */
    public void render() {
        main.batch.begin();
        int i = 0;
        float iy = y + height - 40 - glyph.height/2 - 80;
        float ix = x + width/2 - 210;
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
}
