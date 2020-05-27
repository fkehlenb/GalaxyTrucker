package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

/**
 * button used to buy something in the shop
 */
public class ShopBuyButton extends Button {

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

    //Attribut um zu speichern zu welchem item dieser knopf gehört

    /**
     * Constructor
     *
     * @param main - main class
     */
    public ShopBuyButton(Main main) {

    }

    public void leftClick()
    {
        down = !down;
    }
}
