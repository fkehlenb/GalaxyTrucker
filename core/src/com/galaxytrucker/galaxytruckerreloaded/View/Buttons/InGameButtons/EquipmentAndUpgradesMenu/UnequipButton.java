package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.EquipmentAndUpgradesMenu;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory.InventoryWeaponSlotUI;

public class UnequipButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * weapon to be equipped
     */
    private Weapon weapon;

    /**
     * the inventory ui, if button on inventory
     * otherwise null
     */
    private InventoryWeaponSlotUI inventory;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        inventory.unequipWeapon(weapon);
    }

    /**
     * constructor
     * @param inventory the inventory ui this is on, or null
     */
    public UnequipButton(float x, float y, float width, float height, Weapon weapon, InventoryWeaponSlotUI inventory) {
        super(new Texture("buttons/unequip_button.png"), x, y, width, height);
        this.weapon = weapon;
        this.inventory = inventory;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
