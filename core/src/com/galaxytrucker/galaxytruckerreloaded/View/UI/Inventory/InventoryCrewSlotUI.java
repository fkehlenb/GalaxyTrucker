package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.Texture;

import java.util.LinkedList;
import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;

/**
 * to represent a crew member in the inventory
 */
public class InventoryCrewSlotUI extends InventorySlotUI {

    /**
     * the texture
     */
    private Texture crewTexture;

    /**
     * the textures to display the health status
     */
    private Texture healthStatus;

    /**
     * the surrounding box for the health bar
     */
    private Texture healthBox;

    /**
     * the name of the crew member
     */
    private String name;

    /**
     * the health of the crew member
     */
    private int health;

    /**
     * the maximum health of the crew member
     */
    private int maxhealth;

    private int currTexture;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public InventoryCrewSlotUI(Main main, Crew crew, float x, float y) {
        super(main, x, y);

        name = crew.getName();
        health = crew.getHealth();
        maxhealth = crew.getMaxhealth();

        currTexture = (health/maxhealth) * 10;

        healthStatus = new Texture("gameuis/energybar.png");
        healthBox = new Texture("crew/health_box.png");

        if(crew.getName().equals("ana")) {
            crewTexture = new Texture("crew/anaerobic.png");
        }
        else if(crew.getName().equals("battle")) {
            crewTexture = new Texture("crew/battle.png");
        }
        else {
            crewTexture = new Texture("crew/energy.png"); //TODO wie sieht das mit namen aus?
        }
    }

    /**
     * render
     * without stage stuff
     */
    public void render() {
        super.render();
        main.batch.begin();
        main.batch.draw(crewTexture, 0, 0, 0, 0); //TODO xywh
        main.batch.draw(healthBox, 0, 0, 10, 10);
        float x = 0;
        for(int i=0;i<currTexture;i++) {
            main.batch.draw(healthStatus, x, 0, 0, 0); //TODO whxy
            x+=5;
        }
        main.batch.end();
    }

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {
        super.disposeInventorySlotUI();
        crewTexture.dispose();

        healthStatus.dispose();
        healthBox.dispose();
    }

    /**
     * show the ui
     */
    public void showInventorySlotUI() {

    }

    /**
     * Hide inventory slot ui
     */
    public void hideInventorySlotUI() {

    }
}
