package com.galaxytrucker.galaxytruckerreloaded.View.UI.EnemyShipInfo;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public class EnemyHullUI {

    /**
     * the texture for the background
     */
    private Texture hullBackground;

    private Texture hullTexture;

    private int amount;

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * constructor
     * @param main the main class
     */
    public EnemyHullUI(Main main, int amount) {
        this.main = main;
        this.amount = amount;

        hullTexture = new Texture("gameuis/hull.png");
        hullBackground = new Texture("gameuis/enemyHull.png");
    }

    /**
     * render the textures
     */
    public void render() {
        main.batch.begin();
        float x = 25;
        for(int i=0; i<=amount; i++) {
            main.batch.draw(hullTexture, 14*Main.WIDTH/(20) + x, Main.HEIGHT - Main.HEIGHT/(10f), 25, 25);
            x+=12;
        }
        main.batch.draw(hullBackground, 14*Main.WIDTH/(20), Main.HEIGHT - Main.HEIGHT/(10f), Main.WIDTH/hullBackground.getWidth(), Main.HEIGHT/hullBackground.getHeight());
        main.batch.end();
    }

    /**
     * the status needs to be updated
     * @param status the new status
     */
    public void hullStatusUpdate(int status) {
        amount = status;
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the enemy hull ui
     */
    public void showEnemyHullUI() {

    }

    /**
     * hide the enemy hull ui
     */
    public void hideEnemyHullUI() {

    }

    /**
     * dispose of the enemy hull UI
     */
    public void disposeEnemyHullUI() {
        hullBackground.dispose();
        hullTexture.dispose();
    }
}
