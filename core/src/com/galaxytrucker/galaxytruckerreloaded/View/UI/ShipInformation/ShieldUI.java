package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

public class ShieldUI extends SubsystemUI {

    /**
     * the texture for display in the actual gameplay
     */
    private Texture onShip;

    /**
     * dot for upper left corner
     */
    private Texture shieldTexture;

    /**
     * background for upper left corner
     */
    private Texture upperBackground;

    /**
     * the amount of dots in upper left corner
     */
    private int currentTexture;

    private float shipX, shipY;

    private float shieldWidth, shieldHeight;

    private float upperX, upperY;

    private boolean disabled = false;

    /**
     * Constructor
     *
     * @param main - the main class
     * @param shield the shield
     */
    public ShieldUI(Main main, Stage stage, ShipView ship, float x, float y, System shield, float sx, Stage normalStage, float shipX, float upperX, float upperY, int shields) {
        super(main, stage, ship, x, y, shield, sx, normalStage);
        this.shipX = shipX;
        this.upperX = upperX;
        this.upperY = upperY;

        onShip = new Texture("ship/"+ship.getShipType()+"/shield.png");
        shieldTexture = new Texture("gameuis/shield_top.png");
        upperBackground = new Texture("gameuis/shields.png");

        shieldWidth = onShip.getWidth()*1.5f;
        shieldHeight = onShip.getHeight()*1.5f;

        this.shipY = Main.HEIGHT/2f - shieldHeight/2f;

        currentTexture = shields;
    }

    /**
     * the status of the system was updated either by damage or by repair
     * here
     *
     * @param damage the current status, with 0 being completely functional
     */
    @Override
    public void systemStatusUpdate(int damage) {
        //TODO what in ship is this?
    }

    /**
     * Dispose of shield ui
     */
    public void disposeRoomUI() {
        super.disposeRoomUI();

        shieldTexture.dispose();
        upperBackground.dispose();

        onShip.dispose();

    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        super.render();

        main.batch.begin();
        if(!disabled) {
            main.batch.draw(onShip, shipX, shipY, shieldWidth, shieldHeight);
        }
        main.batch.draw(upperBackground, upperX, upperY, upperBackground.getWidth()*1.25f, upperBackground.getHeight()*1.25f);
        float x = upperX+18;
        for(int i = 0; i <= currentTexture; i++) {
            main.batch.draw(shieldTexture, x, upperY+17, shieldTexture.getWidth(), shieldTexture.getHeight());
            x += shieldTexture.getWidth();
        }
        main.batch.end();
    }

    /**
     * the room was updated in the backend and the display needs to be updated
     *
     * @param room the room with updated stats
     */
    public void update(Room room, int shields) {
        super.update(room);

        disabled = ((System) room).isDisabled();
        currentTexture = shields;
    }
}
