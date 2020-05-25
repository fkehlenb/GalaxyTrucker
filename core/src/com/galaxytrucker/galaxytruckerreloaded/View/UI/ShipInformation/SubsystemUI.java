package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;

/**
 * shows the subsystems of the ship
 */
public class SubsystemUI {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;

    /** Orthographic camera */
    private OrthographicCamera camera;

    /**
     * the system
     */
    private System system;

    /**
     * the textures to display the system in its current damage level
     * use for both the room on the ship and the bottom left corner
     */
    private List<Texture> systemTexture;

    /**
     * the textures for the energy
     * bottom left corner
     */
    private List<Texture> energyTexture;

    /**
     * x position of the room
     */
    private float x;

    /**
     * y position of the room
     */
    private float y;

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the Subsystem ui
     */
    public void showSubsystemUI() {

    }

    /**
     * hide the Subsystem ui
     */
    public void hideSubsystemUI() {

    }

    /**
     * dispose of the Subsystem UI
     */
    public void disposeSubsystemUI() {

    }

    /**
     * updates the energy of the system (bottom left)
     * @param energy the current energy level
     */
    public void energyUpdate(int energy) {

    }

    /**
     * the status of the system was updated either by damage or by repair
     * @param damage the current status, with 0 being completely functional
     */
    public void systemStatusUpdate(int damage) {

    }

    /**
     * constructor
     * @param main the main class
     * @param system the system
     */
    public SubsystemUI(Main main, System system) {

    }
}
