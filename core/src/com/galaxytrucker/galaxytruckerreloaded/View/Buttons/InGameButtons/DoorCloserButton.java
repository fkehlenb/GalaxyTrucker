package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
//import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Section;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

/**
 * Button for closing Door(s) of a Ship-Section
 */
public class DoorCloserButton extends Button
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

    //private System system;
    private Ship ship;
    //private Section section;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public DoorCloserButton(Main main) {
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
     * Closes Door
     */
    public void leftClick()
    {
//        for(Section section : system.getShip().getSection().values())
//        {
//            for(Door door : section.getDoors())
//            {
//                door.forceOpen = false;
//            }
//        }
    }
}

