package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

public class RoomUI {

    /**
     * the system of this room
     */
    private SubsystemUI system;

    /**
     * the normal texture of the room
     */
    private Texture roomTexture;

    /**
     * the texture for the case that the room is low on oxygen
     */
    private Texture roomLowOxyTexture;

    private Room room;

    private Main main;

    private ShipView ship;

    /**
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     * @param room the room
     */
    public RoomUI(Main main, Room room, Stage stage, ShipView ship) {
        this.main = main;
        this.room = room;
        // TODO
//        height = room.getHeight();
//        width = room.getWidth();

        //System sys = TODO wie system?
        //dann neue systemui mit stage
    }

    /**
     * renders everything this room consists of
     */
    public void render() {
        system.render();
        //und textures TODO räume schöner abtrennen? aktuell einfach generell + symbole
    }

    /**
     * Dispose of room ui
     */
    public void disposeRoomUI() {
        system.disposeSubsystemUI();
        //texturen
    }

    /**
     * energy status update from controller
     */
    public void systemEnergyUpdate(int energy) {
        system.energyUpdate(energy);
    }

    /**
     * system status update from controller
     */
    public void systemStatusUpdate(int damage) {
        system.systemStatusUpdate(damage);
    }

    /**
     * the player chooses a new amount
     * @param amount how much should be subtracted/added
     */
    public void systemEnergyChosen(int id, int amount) {
        ship.roomSystemEnergyChosen(room, amount);
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
     * the room was targeted by a weapon of an enemy ship
     */
    public void roomTarget() {

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
