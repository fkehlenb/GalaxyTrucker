package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;

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
     * the texture to display the amount of energy not given to a system yet
     */
    private Texture emptyEnergyTexture;

    /**
     * the amount of energy left not allocated to systems
     */
    private int energy;

    /**
     * amount of blocks on the bottom right corner
     */
    private int maxEnergy;

    private Main main;

    private List<System> existingSystems;

    /**
     * constructor
     * @param main the main class
     * @param energy the amount of energy unallocated to systems
     */
    public EnergyUI(Main main, int energy, List<System> existingSystems) {
        this.main = main;
        this.energy = energy;
        this.maxEnergy = energy;

        this.existingSystems = existingSystems;
        for (System sys: existingSystems
             ) {
            maxEnergy += sys.getEnergy();
        }

        energyTexture = new Texture("gameuis/energybar.png");
        emptyEnergyTexture = new Texture("gameuis/emptyenergybar.png");
    }

    /**
     * the current status of the energy needs to be updated
     * @param energyStatus the new total status
     */
    public void energyUpdate(int energyStatus) {
        energy = energyStatus;
        //TODO currentTexture berechnen
    }

    public void render() {

        main.batch.begin();
        float y = 100;
        for(int i =0; i<=maxEnergy-1; i++) {
            main.batch.draw(emptyEnergyTexture, 25, y, 44, 13);
            y+=20;
        }
        float y2 = 100;
        for(int i =0; i<=energy-1; i++) {
            main.batch.draw(energyTexture, 25, y2, 44, 13);
            y2+=20;
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
