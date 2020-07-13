package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.InventoryCloseButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

public class InventoryUI {

    /**
     * Inventory background texture
     */
    private Texture inventoryBackground;

    /**
     * Inventory slots
     */
    private List<InventorySlotUI> slots;

    /**
     * button to close inventory
     */
    private InventoryCloseButton closeButton;

    private Main main;

    private Stage stage;

    private ShipView shipView;

    private float x, y;

    /**
     * Constructor
     *
     * @param main - main class
     * @param crew the crew members
     * @param weapons the weapons
     */
    public InventoryUI(Main main, List<Crew> crew, List<Weapon> weapons, int fuel, int missiles, Stage stage, ShipView shipView, BitmapFont font) {
        this.main = main;
        this.stage = stage;
        this.shipView = shipView;

        inventoryBackground = new Texture("inventory/inventory.png");

        x = main.WIDTH/2 - inventoryBackground.getWidth()/2;
        y = main.HEIGHT/2 - inventoryBackground.getHeight()/2;

        closeButton = new InventoryCloseButton(x+700, y+15, 248, 50, null, this);
        stage.addActor(closeButton);

        slots = new LinkedList<>();
        float cx = x + 25;
        float cy = y + 560;
        for(Crew c : crew) {
            slots.add(new InventoryCrewSlotUI(main, c, cx, cy, font));
            cy -= 80;
        }
        float wy = y + 525;
        float wx = cx + 400;
        for(Weapon w : weapons) {
            slots.add(new InventoryWeaponSlotUI(main, w, wx, wy, font));
            wy -=100;
        }
        slots.add(new InventoryIntSlotUI(main, fuel, x+50, y+50, "fuel", font));
        slots.add(new InventoryIntSlotUI(main, missiles, x+150, y+50, "missiles", font));
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(inventoryBackground, x, y, 900, 706);
        main.batch.end();

        for(InventorySlotUI u : slots) {
            u.render();
        }
    }

    /**
     * Dispose of inventory
     */
    public void disposeInventoryUI() {
        inventoryBackground.dispose();
        for(InventorySlotUI u : slots) {
            u.disposeInventorySlotUI();
        }
        shipView.deleteInventory();
        closeButton.remove();
    }

    /**
     * setup called after initialisation
     *
     * here the inventory slots are initialised for fuel, missiles, crew, weapons, and money
     */
    private void setup() {
    }

    /**
     * show the inventory
     */
    public void showInventoryUI() {
    }

    /**
     * hide the inventory
     */
    public void hideInventoryUI() {
    }
}
