package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.TileButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.EnemyShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

public class RoomUI {


    /**
     * the texture for the case that the room is low on oxygen
     */
    protected Texture roomLowOxyTexture;

    /**
     * the texture of the tiles the room is made up of
     */
    private Texture tileTexture;

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
        this.x = x;
        this.y = y;

        tileTexture = new Texture("ship/tile.png");

        if(ship instanceof ShipView) {
            for (Tile t : room.getTiles()) {
                stage.addActor(new TileButton(x + (t.getPosX() * 48), y + (t.getPosY() * 48), 48, 48, this));
            }
        }
        else if(ship instanceof EnemyShip) {
            for(Tile t : room.getTiles()) {
                stage.addActor();
            }
        }
    }

    /**
     * renders everything this room consists of
     */
    public void render() {
        main.batch.begin();
        /*for(Tile t : room.getTiles()) {
            main.batch.draw(tileTexture, x + (t.getPosX()*48), y + (t.getPosY()*48), 48, 48);
        }*/
        main.batch.end();
    }

    /**
     * Dispose of room ui
     */
    public void disposeRoomUI() {

    }

    /**
     * animation for the case that the room was hit
     */
    public void roomHitAnimation() {

    }

    /**
     * the rooms texture changes because it is low on oxygen
     *
     */
    public void roomLowOnOxygen() {

    }

    /**
     * the amount of energy given to a system (if this room is a system) is changed
     * @param amount the new amount
     */
    public void systemEnergyUpdate(int amount) { //TODO

    }

    /**
     * the status of the system (if this room is a system) is changed
     * @param amount the new amount
     */
    public void systemStatusUpdate(int amount) { //TODO
    }

    /**
     * the room was targeted by a weapon of an enemy ship
     */
    public void roomTarget() {

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
