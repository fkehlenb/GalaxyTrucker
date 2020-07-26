package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.TileButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.EnemyShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

public class RoomUI {

    protected Room room;

    protected Main main;

    protected AbstractShip ship;

    /**
     * x position of the room
     */
    protected float x;

    /**
     * y position of the room
     */
    protected float y;

    protected Stage stage;

    /**
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     * @param room the room
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
                this.stage.addActor(new TileButton(x + (t.getPosX() * 48), y + (t.getPosY() * 48), 48, 48, this));
            }
        }
        else if(ship instanceof EnemyShip) {
            for(Tile t : room.getTiles()) {
                this.stage.addActor(new TileButton(x + (t.getPosY() * 48), y + (t.getPosX() * 48), 48, 48, this));
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
    }

    /**
     * Dispose of room ui
     */
    public void disposeRoomUI() {
            for (Actor a : stage.getActors()) {
                a.remove();
            }
    }

    /**
     * the amount of energy given to a system (if this room is a system) is changed
     * @param amount the new amount
     */
    public void systemEnergyUpdate(int amount) {

    }

    /**
     * the status of the system (if this room is a system) is changed
     * @param amount the new amount
     */
    public void systemStatusUpdate(int amount) {
    }

    /**
     * the room was chosen with a tile button
     */
    public void chosen() {
        ship.roomChosen(room);
    }

    /**
     * setup called after initialisation
     *
     *
     */
    private void setup() {
    }

    /**
     * show the room ui
     */
    public void showRoomUI() {
    }

    /**
     * hide the room ui
     */
    public void hideRoomUI() {
    }
}
