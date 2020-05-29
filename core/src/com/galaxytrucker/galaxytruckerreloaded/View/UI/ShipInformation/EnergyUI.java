package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.badlogic.gdx.scenes.scene2d.ui.List;

/**
 * shows the energy the ship has
 */
public class EnergyUI {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;

    /** Orthographic camera */
    private OrthographicCamera camera;

    /**
     * the textures to display the overall amount of
     * energy that was not yet given to any system
     */
    private List<Texture> energyTextures;

    /**
     * the amount of energy left not allocated to systems
     */
    private int energy;

    /**
     * the current status of the energy needs to be updated
     * @param energyStatus the new status
     */
    public void energyUpdate(int energyStatus) {

    }


    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the energy ui
     */
    public void showEnergyUI() {

    }

    /**
     * hide the energy ui
     */
    public void hideEnergyUI() {

    }

    /**
     * dispose of the EnergyUI
     */
    public void disposeEnergyUI() {

    }



    /**
     * constructor
     * @param main the main class
     * @param energy the amount of energy unallocated to systems
     */
    public EnergyUI(Main main, int energy) {

    }
}
