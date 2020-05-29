package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * HullUI
 */
public class HullUI {

    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * HullUI background texture
     */
    private Texture HullBackgroundTexture;

    /**
     * Change amount of hull integrity based on hp
     */
    private List<Texture> hullTextures;

    /**
     * the current status
     */
    private int status;

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
    }

    /**
     * the status of the hull was updated, meaning a new texture needs to be displayed
     */
    public void updateStatus() {

    }

    /**
     * Constructor
     *
     * @param main - main class
     * @param status the current status of the hull
     */
    public HullUI(Main main, int status) {
    }
}
