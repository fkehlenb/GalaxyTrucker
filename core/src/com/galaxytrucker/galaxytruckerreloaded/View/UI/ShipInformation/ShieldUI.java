package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Shield;

public class ShieldUI extends SubsystemUI {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;

    /** Orthographic camera */
    private OrthographicCamera camera;

    /**
     * the texture for display in the actual gameplay
     */
    private Texture onShip;

    /**
     * for display in the upper left corner
     */
    private Texture statusTexture;

    /**
     * Shield textures
     */
    private List<Texture> shieldTextures;

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Show shield ui
     */
    public void showShieldUI() {
    }

    /**
     * hide the shield ui
     */
    public void hideShieldUI() {
    }

    /**
     * Dispose of shield ui
     */
    public void disposeShieldUI() {
    }

    /**
     * shield was hit
     */
    public void shieldHitAnimation() {

    }

    /**
     * the status of the system was updated either by damage or by repair
     * here, the upper left corner needs to be factored in
     *
     * @param damage the current status, with 0 being completely functional
     */
    @Override
    public void systemStatusUpdate(int damage) {

    }

    /**
     * Constructor
     *
     * @param main - the main class
     * @param shield the shield
     */
    public ShieldUI(Main main, Shield shield) {
        super(main, shield);
    }
}
