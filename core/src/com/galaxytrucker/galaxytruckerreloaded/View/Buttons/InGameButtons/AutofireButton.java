package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

/**
 * Button to activate Autofire-Function during fights
 */
public class AutofireButton extends Button
{
    boolean down = false;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public AutofireButton(Main main) {
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

    public AutofireButton(int i, int j, Texture autofire_up)
    {
        // super(i, j, autofire_up);
    }

    public void leftClick()
    {
        down = !down;
    }


}