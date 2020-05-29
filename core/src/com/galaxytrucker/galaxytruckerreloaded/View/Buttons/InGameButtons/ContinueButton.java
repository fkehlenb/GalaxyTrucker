package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionsUI;

/**
 * continue button for ingame options
 */
public class ContinueButton extends Button {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;
    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;
    /**
     * Background
     */
    private Texture background;
    /**
     * Click sound effect
     */
    private Sound clickSound;

    boolean down = false;

    /**
     * the options ui this button is on
     */
    private OptionsUI ui;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {

    }

    /**
     * constructor
     * @param main the main class
     * @param ui the ui this is on
     */
    public ContinueButton(Main main, OptionsUI ui) {

    }
}
