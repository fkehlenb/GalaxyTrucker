package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.PVPCloseButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.PVPStartButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.ArrayList;
import java.util.List;


public class PVPOpponents {

    /**
     * the background texture
     */
    private Texture background;

    /**
     * the font to display the names of the pvp opponents
     */
    private BitmapFont font;

    /**
     * the textfield to enter the name of the enemy you want to attack
     */
    private TextField field;

    /**
     * the glyph layouts for the names
     */
    private List<GlyphLayout> glyphs;

    /**
     * the screen this is on
     */
    private GamePlay screen;

    /**
     * the main class exending game with the sprite batch
     */
    private Main main;

    /**
     * the position (lower left)
     */
    private float x, y;

    /**
     * the close button
     */
    private PVPCloseButton closeButton;

    /**
     * the button to start a pvp game
     */
    private PVPStartButton startButton;

    /**
     * constructor
     * @param main the main class
     * @param stage the stage for text field
     * @param font the font
     * @param names the names of the other clients
     */
    public PVPOpponents(Main main, Stage stage, BitmapFont font, List<String> names, GamePlay screen) {
        this.font = font;
        this.screen = screen;
        this.main = main;

        background = new Texture("event/eventbackground.png");
        x = Main.WIDTH/2f - background.getWidth()/2f;
        y = Main.HEIGHT/2f - background.getHeight()/2f;

        closeButton = new PVPCloseButton(x, y + 20, 248, 50, this);
        stage.addActor(closeButton);
        startButton = new PVPStartButton(x + background.getWidth() - 248, y+20, 248, 50, this);
        stage.addActor(startButton);

        glyphs = new ArrayList<>();
        if(names.size() > 0) {
            for (String s : names) {
                GlyphLayout g = new GlyphLayout();
                g.setText(font, s);
                glyphs.add(g);
            }
        }
        else {
            GlyphLayout g = new GlyphLayout();
            g.setText(font, "No enemies were found");
            glyphs.add(g);
        }

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        field = new TextField("", skin);
        field.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
        field.setPosition(Main.WIDTH/2f - field.getWidth()/2, y+20);

        stage.addActor(field);
    }

    /**
     * choose an opponent
     * called by button
     */
    public void chooseOpponent() {
        screen.pvpOpponentChosen(field.getText());
    }

    /**
     * render
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(background, x, y, background.getWidth(), background.getHeight());
        float gy = y + background.getHeight() - 20;
        for(GlyphLayout g : glyphs) {
            font.draw(main.batch, g, main.WIDTH/2 - g.width/2, gy);
            gy -= g.height + 5;
        }
        main.batch.end();

    }

    /**
     * dispose everything
     */
    public void disposePVPOpponents() {
        background.dispose();
        field.remove();
        startButton.remove();
        closeButton.remove();
        screen.deletePVPUI();
    }
}
