package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;

public class RoomUI {

    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * the room
     */
    private Room room;

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

    /**
     * Dispose of room ui
     */
    public void disposeRoomUI() {
    }

    /**
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     * @param room the room
     */
    public RoomUI(Main main, Room room) {
    }
}
