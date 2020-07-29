package com.galaxytrucker.galaxytruckerreloaded.View.UI.EnemyShipInfo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.RoomUI;

import java.util.ArrayList;
import java.util.List;

/**
 * to display the enemy systems
 */
public class EnemySystemUI extends RoomUI {

    /**
     * the textures used (normal and disabled)
     */
    private List<Texture> systemTextures;

    /**
     * which of the textures from the list is currently in use
     */
    private int currentTexture;

    /**
     * constructor
     * @param main main class extending game
     * @param room room this represents (always a system)
     * @param stage stage for buttons
     * @param ship ship ui this belongs to
     * @param x x position (lower left corner)
     * @param y y position (lower left corner)
     *
     * */
    public EnemySystemUI(Main main, Room room, Stage stage, AbstractShip ship, float x, float y) {
        super(main, room, stage, ship, x, y);

        String sysType = ((System) room).getSystemType().toString().toLowerCase();

        systemTextures = new ArrayList<>();
        systemTextures.add(new Texture("shipsys/"+sysType+"/"+sysType+"overlay.png"));
        systemTextures.add(new Texture("shipsys/"+sysType+"/"+sysType+"red.png"));

        currentTexture = ((System) room).isDisabled() ? 1: 0;
    }

    /**
     * renders everything this room consists of
     */
    @Override
    public void render() {
        super.render();
        main.batch.begin();
        main.batch.draw(systemTextures.get(currentTexture), (x + 24 + (24*room.getTiles().get(room.getTiles().size()-1).getPosX()))-16, (y + 24 + (24*room.getTiles().get(room.getTiles().size()-1).getPosY()))-16, 32, 32);
        main.batch.end();
    }

    /**
     * Dispose of room ui
     */
    @Override
    public void disposeRoomUI() {
        super.disposeRoomUI();
        for(Texture t : systemTextures) {
            t.dispose();
        }
    }

    /**
     * the room was updated in the backend and the display needs to be updated
     *
     * @param room the room with updated stats
     */
    @Override
    public void update(Room room) {
        super.update(room);
        currentTexture = ((System) room).isDisabled() ? 1 : 0;
    }
}
