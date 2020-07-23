package com.galaxytrucker.galaxytruckerreloaded.View.UI.EnemyShipInfo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.RoomUI;

public class EnemySystemUI extends RoomUI {

    /***
     * the texture for displaying the system in the ship
     */
    private Texture systemTexture;

    /**
     * the constructor
     * @param main the main class
     */
    public EnemySystemUI(Main main, Room room, Stage stage, AbstractShip ship, float x, float y, System system) {
        super(main, room, stage, ship, x, y);

        String sysType = system.getSystemType().toString().toLowerCase();
        systemTexture = new Texture("shipsys/"+sysType+"/"+sysType+"overlay.png");
    }

    /**
     * renders everything this room consists of
     */
    @Override
    public void render() {
        super.render();
        main.batch.begin();
        main.batch.draw(systemTexture, (x + 24 + (24*room.getTiles().get(room.getTiles().size()-1).getPosX()))-16, (y + 24 + (24*room.getTiles().get(room.getTiles().size()-1).getPosY()))-16, 32, 32);
        main.batch.end();
    }

    /**
     * Dispose of room ui
     */
    @Override
    public void disposeRoomUI() {
        super.disposeRoomUI();
        systemTexture.dispose();
    }

    /**
     * the status of the system changed
     * @param damage the current amount of damage to the system
     */
    public void statusChange(int damage) {

    }
}
