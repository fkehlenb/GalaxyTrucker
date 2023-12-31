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

    /**
     * main class extending game
     */
    private Main main;

    /**
     * Constructor
     *
     * @param main - main class
     * @param status the current status of the hull
     */
    public HullUI(Main main, int status) {
        this.main = main;
        this.status = status;

        hullBackgroundTexture = new Texture("gameuis/top_hull.png");
        hullTexture = new Texture("gameuis/hull.png");

    }

    /**
     * the status of the hull was updated, meaning a new texture needs to be displayed
     * @param status the new total status
     */
    public void updateStatus(int status) {
        this.status = status;
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        float x = 25;
        for(int i=0; i<=status; i++) {
            main.batch.draw(hullTexture, x, Main.HEIGHT - 83, 25, 25);
            x+=12;
        }
        main.batch.draw(hullBackgroundTexture, 25, Main.HEIGHT - 112, 300, 97);
        main.batch.end();
    }

    /**
     * dispose
     */
    public void disposeHullUI() {
        hullBackgroundTexture.dispose();
        hullTexture.dispose();
    }
}
