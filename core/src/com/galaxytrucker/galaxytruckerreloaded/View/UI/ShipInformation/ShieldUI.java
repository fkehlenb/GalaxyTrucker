package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Shield;

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

    /**
     * Constructor
     *
     * @param main - the main class
     * @param shield the shield
     */
    public ShieldUI(Main main, Shield shield, Stage stage, RoomUI roomUI) {
        super(main, shield, stage, roomUI);

        onShip = new Texture("ship/anaerobic/shields.png");

        shieldTexture = new Texture("gameuis/shield_top.png");
        upperBackground = new Texture("gameuis/shield_topbackground.png");

        currentTexture = 4;
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
    public void disposeShieldUI() {
        super.disposeSubsystemUI();

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
        main.batch.draw(onShip, 0, 0, 0 ,0); //TODO whxy
        main.batch.draw(upperBackground, 0, 0, 10, 10);
        float x = 0; //TODO
        for(int i = 0; i <= currentTexture; i++) {
            main.batch.draw(shieldTexture, x, 0, 10, 10); //TODO whxy
            x +=10; //TODO
        }
        main.batch.end();
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Show shield ui
     */
    public void showShieldUI() {
    }

    /**
     * hide the shield ui
     */
    public void hideShieldUI() {
    }

    /**
     * shield was hit
     */
    public void shieldHitAnimation() {

    }
}
