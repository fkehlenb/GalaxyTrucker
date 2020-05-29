package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ShipSelector;

/**
 * button to choose single player in the ship selector
 */
public class SinglePlayerButton extends Button {

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

    /**
     * the screen this button is on
     */
    private ShipSelector screen;

    /**
     * whether or not it is single player
     */
    private boolean singlePlayer;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {

    }

    /**
     * constructor
     *
     * @param screen  the screen this button is on
     * @param main the main class
     */
    public SinglePlayerButton(Main main, ShipSelector screen) {

    }
}
