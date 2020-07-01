package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;


import com.badlogic.gdx.graphics.Texture;

import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * HullUI
 */
public class HullUI {

    /**
     * HullUI background texture
     */
    private Texture hullBackgroundTexture;

    /**
     * Change amount of hull integrity based on hp
     */
    private Texture hullTexture;

    /**
     * the current status
     */
    private int status;

    private Main main;

    /**
     * how many bars in hullTexture?
     */
    private int currentTexture;

    /**
     * Constructor
     *
     * @param main - main class
     * @param status the current status of the hull
     */
    public HullUI(Main main, int status) {
        this.main = main;
        this.status = status;

        currentTexture = 8;

        hullBackgroundTexture = new Texture("gameuis/top_hull.png");
        hullTexture = new Texture("gameuis/hull.png");

    }

    /**
     * the status of the hull was updated, meaning a new texture needs to be displayed
     */
    public void updateStatus(int status) {
        this.status = status;
        //TODO richtige texture berechnen
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        float x = 25;
        for(int i=0; i<=currentTexture; i++) {
            main.batch.draw(hullTexture, x, main.HEIGHT - 83, 25, 25);
            x+=12;
        }
        main.batch.draw(hullBackgroundTexture, 25, main.HEIGHT - 112, 577, 97);
        main.batch.end();
    }

    /**
     * setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show ui
     */
    public void showHullUI() {
    }

    /**
     * hide ui
     */
    public void hideHullUI() {
    }

    /**
     * dispose
     */
    public void disposeHullUI() {
        hullBackgroundTexture.dispose();
        hullTexture.dispose();
    }
}
