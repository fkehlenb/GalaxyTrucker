package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;

public class ShipView {

    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * Ship used by this view
     */
    private Ship ship;

    /**
     * Background image if its the enemy ship
     */
    private Texture enemyShipBackgroundTexture;

    /**
     * Ship shield view
     */
    private ShieldView shieldView;

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Show the ship
     */
    public void showShipView() {
    }

    /**
     * Hide the ship
     */
    public void hideShipView() {
    }

    /**
     * Dispose of ship
     */
    public void disposeShipView() {
    }

    /**
     * Ship hop animation
     */
    private void hyperspeedHopAnimation() {
    }

    /**
     * Shield destroyed animation
     */
    private void shipDestroyedAnimation() {
    }

    /**
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     */
    public ShipView(Main main) {
    }
}
