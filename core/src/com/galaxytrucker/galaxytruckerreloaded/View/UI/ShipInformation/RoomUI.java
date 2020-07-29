package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.TileButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.EnemyShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

import java.util.ArrayList;
import java.util.List;

/**
 * ui for displaying a room
 */
public class RoomUI {

    /**
     * the room represented by this ui
     */
    protected Room room;

    /**
     * main class extending game
     */
    protected Main main;

    /**
     * ship this room belongs to
     */
    protected AbstractShip ship;

    /**
     * x position of the room
     */
    protected float x;

    /**
     * y position of the room
     */
    protected float y;

    /**
     * stage for buttons
     */
    protected Stage stage;

    /**
     * list of tile buttons belonging to this room
     */
    private List<TileButton> enemyTiles = new ArrayList<>();

    /**
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     * @param room the room
     * @param stage stage for buttons
     * @param ship ship this room belongs to
     * @param x x position
     * @param y y position
     */
    public RoomUI(Main main, Room room, Stage stage, AbstractShip ship, float x, float y) {
        this.main = main;
        this.room = room;
        this.ship = ship;
        this.stage = stage;
        this.x = x;
        this.y = y;



        if(ship instanceof ShipView) {
            for (Tile t : room.getTiles()) {
                TileButton tileButton = new TileButton(x + (t.getPosX() * 48), y + (t.getPosY() * 48), 48, 48, this);
                this.stage.addActor(tileButton);
                enemyTiles.add(tileButton);
            }
        }
        else if(ship instanceof EnemyShip) {
            for(Tile t : room.getTiles()) {
                TileButton tileButton = new TileButton(x + (t.getPosY() * 48), y + (t.getPosX() * 48), 48, 48, this);
                this.stage.addActor(tileButton);
                enemyTiles.add(tileButton);
            }
        }
    }

    /**
     * renders everything this room consists of
     */
    public void render() {
    }

    /**
     * the room was updated in the backend and the display needs to be updated
     * @param room the room with updated stats
     */
    public void update(Room room) {
        this.room = room;
        if(room.getBreach() > 0) {
            for(TileButton b : enemyTiles) {
                b.breach();
            }
        }
        else {
            for(TileButton b : enemyTiles) {
                b.breachGone();
            }
        }
    }

    /**
     * Dispose of room ui
     */
    public void disposeRoomUI() {
        for (Actor a : enemyTiles) {
            a.remove();
        }
    }

    /**
     * the room was chosen with a tile button
     */
    public void chosen() {
        ship.roomChosen(room);
    }
}
