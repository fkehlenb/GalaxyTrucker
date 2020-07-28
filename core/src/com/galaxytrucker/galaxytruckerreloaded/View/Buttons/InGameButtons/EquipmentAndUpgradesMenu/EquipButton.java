package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.EquipmentAndUpgradesMenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory.InventoryWeaponSlotUI;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;

/**
 * button for equipping a weapon
 */
public class EquipButton extends ImButton {

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
        inventory.equipWeapon(weapon);
    }

    /**
     * constructor
     * @param x x position of button
     * @param y y position of button
     * @param width width of button
     * @param height height of button
     * @param weapon weapon this button belongs to
     * @param inventory inventory ui this button is on
     */
    public EquipButton(float x, float y, float width, float height, Weapon weapon, InventoryWeaponSlotUI inventory) {
        super(new Texture("buttons/equip_button.png"), x, y, width, height);
        this.weapon = weapon;
        this.inventory = inventory;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
