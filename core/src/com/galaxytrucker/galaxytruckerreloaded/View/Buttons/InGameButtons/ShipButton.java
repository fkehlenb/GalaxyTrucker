package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

/**
 * Button for opening the Ship-Interface
 * */
public class ShipButton extends Button
{
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

    private Ship ship;
    /**
     * Constructor
     *
     * @param main - main class
     * @param ship - the current Ship
     */
    public ShipButton(Main main, Ship ship) {
    }


    /**
     * opens the Ship-Screen of the cureent Ship
     */
    public void leftClick()
    {

    }
}
