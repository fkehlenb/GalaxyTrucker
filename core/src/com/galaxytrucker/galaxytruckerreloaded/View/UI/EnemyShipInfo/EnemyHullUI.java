package com.galaxytrucker.galaxytruckerreloaded.View.UI.EnemyShipInfo;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * to display the enemy hull status
 */
public class EnemyHullUI {

    /**
     * the texture for the background
     */
    private Texture hullBackground;

    /**
     * texture for one single status bar
     */
    private Texture hullTexture;

    /**
     * amount of status bars/hull status
     */
    private int amount;

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * constructor
     * @param main the main class
     * @param amount amount of hull currently remaining
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
            //main.batch.draw(hullTexture, 138*Main.WIDTH/(200) + x, Main.HEIGHT - Main.HEIGHT/(11f), 15, 15);
            main.batch.draw(hullTexture, 118*Main.WIDTH/(200f) + x, Main.HEIGHT - Main.HEIGHT/(11f), 15, 15);
            x+=12*0.6f;
        }
        //main.batch.draw(hullBackground, 14*Main.WIDTH/(20), Main.HEIGHT - Main.HEIGHT/(9f), Main.WIDTH/9.74f, Main.HEIGHT/16.61f);
        main.batch.draw(hullBackground, 12*Main.WIDTH/(20f), Main.HEIGHT - Main.HEIGHT/(9f), Main.WIDTH/9.74f, Main.HEIGHT/16.61f);
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
     * dispose of the enemy hull UI
     */
    public void disposeEnemyHullUI() {
        hullBackground.dispose();
        hullTexture.dispose();
    }
}
