package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;

public class InventoryCrewSlotUI extends InventorySlotUI {

    /**
     * the texture
     */
    private Texture crewTexture;

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

    /**
     * the textures to display the health status
     */
    private List<Texture> healthStatus;

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

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {

    }

    /**
     * Constructor
     *
     * @param main - main class
     */
    public InventoryCrewSlotUI(Main main, Crew crew) {
        super(main);
    }
}
