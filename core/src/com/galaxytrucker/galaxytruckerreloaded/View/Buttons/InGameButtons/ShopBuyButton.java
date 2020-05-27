package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopUI;

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

    /**
     * the item this button belongs to
     * index of list in shopui
     */
    private int item;

    /**
     * the ui this button is on
     */
    private ShopUI shop;

    /**
     * Constructor
     *
     * @param main - main class
     * @param item the item
     * @param ui the ui this button is on
     */
    public ShopBuyButton(Main main, int item, ShopUI ui) {

    }

    public void leftClick()
    {
        down = !down;
    }
}
