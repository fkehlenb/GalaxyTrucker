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
 * Button for opening the Door(s) of a Ship-Section
 */
public class DoorOpenerButton extends Button
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

    //private long lastClick = -1;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public DoorOpenerButton(Main main) {
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
     * Makes an explicit Door passable
     */
    public void leftClick()
    {
//        if(lastClick == -1)
//        {
//            lastClick = System.currentTimeMillis();
//        }
//        else
//        {
//            Clock.log("" + (lastClick - System.currentTimeMillis()));
//            if(System.currentTimeMillis() - lastClick < 100)
//            {
//                return;
//            }
//            else
//            {
//                lastClick = System.currentTimeMillis();
//            }
//        }
//
//        boolean changed = false;
//        for(Room room : system.getShip().getRooms().values())
//        {
//            for(Door door : room.getDoors())
//            {
//                if(door.room2 != null)
//                {
//                    if(door.forceOpen == false)
//                    {
//                        changed = true;
//                    }
//                    door.forceOpen = true;
//                }
//            }
//        }
//        Clock.log("test " + changed);
//
//        if(changed == false)
//        {
//            for(Room room : system.getShip().getRooms().values())
//            {
//                for(Door door : room.getDoors())
//                {
//                    door.forceOpen = true;
//                }
//            }
//        }
    }
}
