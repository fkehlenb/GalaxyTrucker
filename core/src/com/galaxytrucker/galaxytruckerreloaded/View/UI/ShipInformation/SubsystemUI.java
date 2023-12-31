package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
     *
     */
    private Texture energyTexture;

    /**
     * the type of system
     */
    private SystemType systemType;

    /**
     * botton representing the system in the bottom left corner.
     * used to give the system energy
     */
    private SystemButton energyButton;

    /**
     * the current energy level
     */
    private int energy;

    /**
     * id of the subsystem
     */
    protected int id;

    /**
     * the amount of energy bars to be displayed
     */
    private int currentStatus;

    /**
     * the x position of the subsystem in the lower left corner
     */
    private float sx;

    /**
     * constructor
     * @param main main class extending game
     * @param tileStage the stage for tile buttons
     * @param ship the ship ui this belongs to
     * @param x x position of the room
     * @param y y position of the room
     * @param system the system to be displayed
     * @param sx the position of the energy button in the lower left corner
     * @param normalStage the normal stage for buttons
     */
    public SubsystemUI(Main main, Stage tileStage, ShipView ship, float x, float y, System system, float sx, Stage normalStage) {
        super(main, system, tileStage, ship, x, y);
        this.sx = sx;
        energy = system.getEnergy();
        systemType = system.getSystemType();
        systemTexture = new java.util.LinkedList<>();
        id = system.getId();

        String sysType = system.getSystemType().toString().toLowerCase();

        currentStatus = system.isDisabled() ? 2 : 3;

        systemTexture.add(new Texture("shipsys/"+sysType+"/"+sysType+"green.png"));
        systemTexture.add(new Texture("shipsys/"+sysType+"/"+sysType+"orange.png"));
        systemTexture.add(new Texture("shipsys/"+sysType+"/"+sysType+"red.png"));
        systemTexture.add(new Texture("shipsys/"+sysType+"/"+sysType+"overlay.png"));

        energyButton = new SystemButton(systemTexture.get(0), sx, 50, 50, 50, this);

        energyTexture = new Texture("gameuis/energybar.png");

        normalStage.addActor(energyButton);
    }

    /**
     * render everything to the screen
     */
    public void render() {
        super.render();
        main.batch.begin();
        float sy = 90;
        for(int i = 0; i<energy; i++) {
            main.batch.draw(energyTexture, sx + 18, sy, 20, 5);
            sy += 8;
        }
        main.batch.draw(systemTexture.get(currentStatus), (x + 24 + (24*room.getTiles().get(room.getTiles().size()-1).getPosX()))-16, (y + 24 + (24*room.getTiles().get(room.getTiles().size()-1).getPosY()))-16, 32, 32);
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
     * the room was updated in the backend and the display needs to be updated
     *
     * @param room the room with updated stats
     */
    @Override
    public void update(Room room) {
        super.update(room);
        System sys = (System) room;
        currentStatus = sys.isDisabled() ? 2 : 3;
        this.energy = sys.getEnergy();
    }

    /**
     * activates the energy supply for this system
     * called by systembutton
     * if energy supply already activated and not at maximum, then more energy to this system
     */
    public void addEnergy() {
        if (systemType==SystemType.SHIELDS)
        {
            ((ShipView) ship).roomSystemEnergyAdded(room, 2);
        }
        else {
            ((ShipView) ship).roomSystemEnergyAdded(room, 1);
        }
    }

    /**
     * subtracts energy supply
     * called by button
     */
    public void removeEnergy() {
        if (systemType==SystemType.SHIELDS)
        {
            ((ShipView) ship).roomSystemEnergyRemoved(room, 2);
        }
        else {
            ((ShipView) ship).roomSystemEnergyRemoved(room, 1);
        }
    }
}
