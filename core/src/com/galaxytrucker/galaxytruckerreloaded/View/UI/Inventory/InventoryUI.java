package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
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
     * the hash map for the inventory slots of the weapons, needed for easy access for updates
     */
    private HashMap<Integer, InventoryWeaponSlotUI> weaponSlots;

    /**
     * button to close inventory
     */
    private InventoryCloseButton closeButton;

    private Main main;

    private ShipView shipView;

    private float x, y;

    /**
     * Constructor
     *
     * @param main - main class
     * @param crew the crew members
     * @param weapons the weapons
     */
    public InventoryUI(Main main, List<Crew> crew, List<Weapon> weapons, List<Weapon> equippedWeapons, int fuel, int missiles, Stage stage, ShipView shipView, BitmapFont font, ShipType type) {
        this.main = main;
        this.shipView = shipView;

        inventoryBackground = new Texture("inventory/inventory.png");

        x = Main.WIDTH/2f - inventoryBackground.getWidth()/2f;
        y = Main.HEIGHT/2f - inventoryBackground.getHeight()/2f;

        closeButton = new InventoryCloseButton(x+Main.WIDTH/2.743f, y+Main.HEIGHT/72f, Main.WIDTH/7.742f, Main.HEIGHT/21.6f, null, this, null);
        stage.addActor(closeButton);

        slots = new LinkedList<>();
        weaponSlots = new HashMap<>();
        float cx = x + Main.WIDTH/76.8f;
        float cy = y + Main.HEIGHT/1.929f;
        for(Crew c : crew) {
            slots.add(new InventoryCrewSlotUI(main, c, cx, cy, font, type));
            cy -= Main.HEIGHT/13.5f;
        }

        float wy = y + Main.HEIGHT/2.057f;
        float wx = cx + Main.WIDTH/4.8f;
        for(Weapon w : weapons) {
            weaponSlots.put(w.getId(), new InventoryWeaponSlotUI(main, w, wx, wy, stage, font, this, false));
            wy -=Main.HEIGHT/10.8f;
        }
        for(Weapon w : equippedWeapons) {
            weaponSlots.put(w.getId(), new InventoryWeaponSlotUI(main, w, wx, wy, stage, font, this, true));
            wy -= Main.HEIGHT/10.8f;
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
        for(InventoryWeaponSlotUI w : weaponSlots.values()) {
            w.render();
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
        for(InventoryWeaponSlotUI w : weaponSlots.values()) {
            w.disposeInventorySlotUI();
        }
        shipView.deleteInventory();
        closeButton.remove();
    }

    /**
     * update the inventory view (unequip/equip weapons)
     */
    public void update(List<Weapon> weapons, List<Weapon> equipped) {
        for(Weapon w : weapons) {
            if(weaponSlots.get(w.getId()) != null) {
                weaponSlots.get(w.getId()).update(w, false);
            }
        }
        for(Weapon w : equipped) {
            if(weaponSlots.get(w.getId()) != null) {
                weaponSlots.get(w.getId()).update(w, true);
            }
        }
    }

    /**
     * setup called after initialisation
     *
     * here the inventory slots are initialised for fuel, missiles, crew, weapons, and money
     */
    private void setup() {
    }

    /**
     * equip a weapon
     * (move from ship inventory to weapon system inventory)
     * @param weapon the weapon
     */
    void equipWeapon(Weapon weapon){
        shipView.equipWeapon(weapon);
    }

    /**
     * unequip a weapon
     * @param weapon the weapon
     */
    void unequipWeapon(Weapon weapon) {
        shipView.unequipWeapon(weapon);
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
