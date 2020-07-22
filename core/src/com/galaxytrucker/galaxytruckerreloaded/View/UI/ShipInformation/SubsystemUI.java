package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.galaxytrucker.galaxytruckerreloaded.Controller.SystemController;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.*;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.SystemButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

/**
 * shows the subsystems of the ship
 */
public class SubsystemUI extends RoomUI {

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

    private SystemType systemType;

    /**
     * botton representing the system in the bottom left corner.
     * used to give the system energy
     */
    private SystemButton energyButton;

    /**
     * Controller that contains logic for managing energy
     */
    SystemController systemController;

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

    private int currentStatus;

    /**
     * the x position of the subsystem in the lower left corner
     */
    private float sx;

    /**
     * constructor
     * @param main the main class
     * @param system the system
     */
    public SubsystemUI(Main main, Stage stage, ShipView ship, float x, float y, System system, float sx) {
        super(main, system, stage, ship, x, y);
        this.sx = sx;
        energy = system.getEnergy();
        maxEnergy = system.getMaxEnergy();
        damage = system.getDamage();
        systemType = system.getSystemType();
        systemController = SystemController.getInstance(null);
        systemTexture = new java.util.LinkedList<>();
        id = system.getId();

        String sysType = system.getSystemType().toString().toLowerCase();

        systemTexture.add(new Texture("shipsys/"+sysType+"/"+sysType+"green.png"));
        systemTexture.add(new Texture("shipsys/"+sysType+"/"+sysType+"orange.png"));
        systemTexture.add(new Texture("shipsys/"+sysType+"/"+sysType+"red.png"));
        systemTexture.add(new Texture("shipsys/"+sysType+"/"+sysType+"overlay.png"));

        energyButton = new SystemButton(systemTexture.get(0), sx, 50, 50, 50, this);

        energyTexture = new Texture("gameuis/energybar.png");

        stage.addActor(energyButton);
    }

    public void render() {
        super.render();
        main.batch.begin();
        float sy = 90;
        for(int i = 0; i<energy; i++) {
            main.batch.draw(energyTexture, sx + 18, sy, 20, 5);
            sy += 8;
        }
        main.batch.draw(systemTexture.get(3), (x + 24 + (24*room.getTiles().get(room.getTiles().size()-1).getPosX()))-16, (y + 24 + (24*room.getTiles().get(room.getTiles().size()-1).getPosY()))-16, 32, 32);
        main.batch.end();
    }

    /**
     * dispose of the Subsystem UI
     */
    public void disposeRoomUI() {
        super.disposeRoomUI();
        energyTexture.dispose();
        for(Texture t : systemTexture) {
            t.dispose();
        }

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
    public void addEnergy() {
        if (systemType==SystemType.SHIELDS)
        {

            systemController.addEnergy((System) room,2);
        }
        else {
            systemController.addEnergy((System) room,1);
        }
    }

    /**
     * subtracts energy supply
     * called by button
     */
    public void removeEnergy() {

        if (systemType==SystemType.SHIELDS)
        {
            systemController.removeEnergy((System) room, 2);
        }
        else {
            systemController.removeEnergy((System) room, 1);
        }
    }
}
