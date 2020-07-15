package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
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
    public InventoryUI(Main main, List<Crew> crew, List<Weapon> weapons, int fuel, int missiles, Stage stage, ShipView shipView, BitmapFont font, ShipType type) {
        this.main = main;
        this.stage = stage;
        this.shipView = shipView;

        inventoryBackground = new Texture("inventory/inventory.png");

        x = main.WIDTH/2 - inventoryBackground.getWidth()/2;
        y = main.HEIGHT/2 - inventoryBackground.getHeight()/2;

        closeButton = new InventoryCloseButton(x+Main.WIDTH/2.743f, y+Main.HEIGHT/72, Main.WIDTH/7.742f, Main.HEIGHT/21.6f, null, this, null);
        stage.addActor(closeButton);

        slots = new LinkedList<>();
        float cx = x + Main.WIDTH/76.8f;
        float cy = y + Main.HEIGHT/1.929f;
        for(Crew c : crew) {
            slots.add(new InventoryCrewSlotUI(main, c, cx, cy, font, type));
            cy -= Main.HEIGHT/13.5f;
        }
        float wy = y + Main.HEIGHT/2.057f;
        float wx = cx + Main.WIDTH/4.8f;
        for(Weapon w : weapons) {
            slots.add(new InventoryWeaponSlotUI(main, w, wx, wy, font));
            wy -=Main.HEIGHT/10.8f;
        }
        slots.add(new InventoryIntSlotUI(main, fuel, x+Main.WIDTH/38.4f, y+Main.HEIGHT/21.6f, "fuel", font));
        slots.add(new InventoryIntSlotUI(main, missiles, x+Main.WIDTH/12.8f, y+Main.HEIGHT/21.6f, "missiles", font));
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(inventoryBackground, x, y, Main.WIDTH/2.133f, Main.HEIGHT/1.53f);
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
