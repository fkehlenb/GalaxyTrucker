package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

/**
 * Starts a new Game
 */
public class StartButton extends Button
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

    /**
     * the screen this button is on
     */
    private MainMenu screen;


    /**
     * Constructor
     *
     * @param main - main class
     * @param screen the screen this button is on
     */
    public StartButton(Main main, MainMenu screen) {
    }

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
//    }

    /**
     * Creates s a new Instance of PLayer an an new Ship
     */
    public void leftClick()
    {
        // FTLGame.instance().setPlayer(hanger.getShip());
        // FTLView.instance().setScreen(new SpaceScreen());
    }
}