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

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * constructor
     * @param main the main class
     */
    public EnemyHullUI(Main main) {
        this.main = main;

        //hullBackground = new Texture();
    }

    /**
     * render the textures
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(hullBackground, 0, 0, hullBackground.getWidth(), hullBackground.getHeight());
        main.batch.end();
    }

    /**
     * the status needs to be updated
     * @param status the new status
     */
    public void hullStatusUpdate(int status) {

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

    }
}
