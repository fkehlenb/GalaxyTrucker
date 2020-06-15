package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;

import java.util.LinkedList;
import java.util.List;

/**
 * shows the energy the ship has
 */
public class EnergyUI {

    /**
     * the texture to display the amount of energy not given to a system yet
     */
    private Texture energyTexture;

    /**
     * the amount of energy left not allocated to systems
     */
    private int energy;

    /**
     * amount of blocks on the bottom right corner
     */
    private int currentTexture;

    private Main main;

    /**
     * constructor
     * @param main the main class
     * @param energy the amount of energy unallocated to systems
     */
    public EnergyUI(Main main, int energy) {
        this.main = main;
        this.energy = energy;

        currentTexture = 8;

        energyTexture = new Texture("gameuis/energybar.png");
    }

    /**
     * the current status of the energy needs to be updated
     * @param energyStatus the new status
     */
    public void energyUpdate(int energyStatus) {
        energy = energyStatus;
        //TODO currentTexture berechnen
    }

    public void render() {
        main.batch.begin();
        float x = 0; //TODO
        for(int i =0; i<=currentTexture; i++) {
            main.batch.draw(energyTexture, x, 0, 0, 0); //TODO xywh
            x+=10;
        }
        main.batch.end();
    }

    /**
     * dispose of the EnergyUI
     */
    public void disposeEnergyUI() {
        energyTexture.dispose();

    }


    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the energy ui
     */
    public void showEnergyUI() {

    }

    /**
     * hide the energy ui
     */
    public void hideEnergyUI() {

    }
}
