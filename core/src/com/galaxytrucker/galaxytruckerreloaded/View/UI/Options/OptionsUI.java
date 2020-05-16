package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * Ingame options UI
 */
public class OptionsUI {

    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * Continue button
     */
    private ImageButton continueButton;

    /**
     * Continue button image
     */
    private Texture continueButtonTexture;

    /**
     * Main Menu button
     */
    private ImageButton mainMenuButton;

    /**
     * Main Menu button texture
     */
    private Texture mainMenuButtonTexture;

    /**
     * Quit button
     */
    private ImageButton quitButton;

    /**
     * Quit button texture
     */
    private Texture quitButtonTexture;

    /**
     * Main class for sprite batch
     */
    private Main mainClass;

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Open the options menu
     */
    public void openOptions() {
    }

    /**
     * Close the options menu
     */
    public void closeOptions() {
    }

    /**
     * Constructor
     */
    public OptionsUI(Main main) {
        setup();
    }
}
