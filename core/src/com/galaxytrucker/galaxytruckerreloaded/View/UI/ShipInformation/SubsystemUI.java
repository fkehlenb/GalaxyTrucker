package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Engine;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Shield;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.WeaponSystem;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.SystemButton;

/**
 * shows the subsystems of the ship
 */
public class SubsystemUI {

    /**
     * the textures to display the system in its current damage level
     * use for both the room on the ship and the bottom left corner
     */
    private java.util.List<Texture> systemTexture;

    /**
     * the textures for the energy
     * bottom left corner
     */
    private Texture energyTexture;

    /**
     * botton representing the system in the bottom left corner.
     * used to give the system energy
     */
    private SystemButton energyButton;

    /**
     * x position of the room
     */
    private float x;

    /**
     * y position of the room
     */
    private float y;

    /**
     * the current energy level
     */
    private int energy;
    /**
     * the maximum energy level
     */
    private int maxEnergy;
    /**
     * the amount of damage to this system
     */
    private int damage;

    /**
     * id of the subsystem
     */
    protected int id;

    protected Main main;

    protected Stage stage;

    private int currentStatus;

    private RoomUI roomUI;

    /**
     * constructor
     * @param main the main class
     * @param system the system
     */
    public SubsystemUI(Main main, System system, Stage stage, RoomUI roomUI) {
        this.main = main;
        this.stage = stage;
        this.roomUI = roomUI;

        x = system.getPosX();
        y = system.getPosY();
        energy = system.getEnergy();
        maxEnergy = system.getMaxEnergy();
        damage = system.getDamage();
        systemTexture = new java.util.LinkedList<>();
        id = system.getId();

        if(system instanceof Engine) {
            systemTexture.add(new Texture("shipsys/engine/enginegreen.png"));
            systemTexture.add(new Texture("shipsys/engine/engineorange.png"));
            systemTexture.add(new Texture("shipsys/engine/enginered.png"));
        }
        else if (system instanceof Shield) {
            systemTexture.add(new Texture("shipsys/shields/shieldsgreen.png"));
            systemTexture.add(new Texture("shipsys/shields/shieldsorange.png"));
            systemTexture.add(new Texture("shipsys/shields/shieldsred.png"));
        }
        else if(system instanceof WeaponSystem) {
            systemTexture.add(new Texture("shipsys/weapon/weapongreen.png"));
            systemTexture.add(new Texture("shipsys/weapon/weaponorange.png"));
            systemTexture.add(new Texture("shipsys/weapon/weaponred.png"));
        }
        energyButton = new SystemButton(systemTexture.get(0), x, y, 15, 15, this); //TODO w, h also x y for corner!! not these

        energyTexture = new Texture("gameuis/energybar.png");

        stage.addActor(energyButton);

        Gdx.input.setInputProcessor(stage);
    }

    public void render() {
        main.batch.begin();
        float x = 0; //TODO
        for(int i = 0; i<energy; i++) {
            main.batch.draw(energyTexture, x, 0, 10, 10);
            x += 5; //TODO?
        }
        //TODO else für alle level
        main.batch.draw(systemTexture.get(currentStatus), 0, 0, 15, 15); //TODO position, w, h
        main.batch.end();
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the Subsystem ui
     */
    public void showSubsystemUI() {

    }

    /**
     * hide the Subsystem ui
     */
    public void hideSubsystemUI() {

    }

    /**
     * dispose of the Subsystem UI
     */
    public void disposeSubsystemUI() {
        energyTexture.dispose();
        for(Texture t : systemTexture) {
            t.dispose();
        }
        stage.dispose();

        energyButton.remove();
    }

    /**
     * updates the energy of the system (bottom left)
     * called by controller
     * @param energy the new amount
     */
    public void energyUpdate(int energy) {
        this.energy = energy;
        //TODO call controller
    }

    /**
     * the status of the system was updated either by damage or by repair
     * called by controller
     * @param damage the current status, with 0 being completely functional
     */
    public void systemStatusUpdate(int damage) {
        if(!(currentStatus == systemTexture.size())) {
            currentStatus++;
        }
    }

    /**
     * activates the energy supply for this system
     * called by systembutton
     * if energy supply already activated and not at maximum, then more energy to this system
     */
    public void activateEnergy() {
        roomUI.systemEnergyChosen(id, 1); //TODO how much
    }

    /**
     * subtracts energy supply
     * called by button
     */
    public void lessEnergy() {
        roomUI.systemEnergyChosen(id, -1);
    }
}
