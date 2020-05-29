package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

/**
 * Button to activate Autofire-Function during fights
 */
public class AutofireButton extends Button
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

    boolean down = false;

    /**
     * the ui this button is on
     */
    private ShipView ui;

    /**
     * Constructor
     *
     * @param main - main class
     * @param ui the ui this button is on
     */
    public AutofireButton(Main main, ShipView ui) {

//    /**
//     * Send data to server
//     */
//    private void sendData(Packet data) {
//    }
//
//    /**
//     * Receive data from server
//     */
//    private Packet receiveData() {
//        return null;
    }

    public void leftClick()
    {
        down = !down;
    }


}