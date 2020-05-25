package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

public class QuitButton extends Button
{
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

    @Override
    public void leftClick()
    {
        System.exit(0);
    }
}

