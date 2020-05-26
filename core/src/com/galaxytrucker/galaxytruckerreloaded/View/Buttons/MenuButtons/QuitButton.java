package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

/**
 * Ends the Game
 */
public class QuitButton extends Button
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
     * Constructor
     *
     * @param main - main class
     */
    public QuitButton(Main main) {
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
     * Ends the Game
     */
    @Override
    public void leftClick()
    {
        System.exit(0);
    }
}

