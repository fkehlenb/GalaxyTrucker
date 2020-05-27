package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

import java.io.BufferedWriter;

/**
 * button used to activate/deactivate weapon
 */
public class WeaponActivateButton extends Button {

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

    //TODO attribut welches weapon dieser knopf ist

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {

    }

    /**
     * constructor
     * @param main the main class
     */
    public WeaponActivateButton(Main main) {

    }
}
