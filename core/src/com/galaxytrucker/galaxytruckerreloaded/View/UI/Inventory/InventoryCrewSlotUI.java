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
        currTexture = (int) (((float) health/maxhealth) * 10);

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
        main.batch.draw(crewTexture, posX, posY, 72, 72); //48
        main.batch.draw(healthBox, posX+5, posY-15, 50, 15);
        float x = posX+11;
        for(int i=0;i<currTexture;i++) {
            main.batch.draw(healthStatus, x, posY-10, 4, 5);
            x+=4;
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
