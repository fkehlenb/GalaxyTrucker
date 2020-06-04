package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory.InventoryUI;

/**
 * used to close the inventory
 */
public class InventoryCloseButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * shop ui, if button on shop
     * otherwise null
     */
    private ShopUI shop;

    /**
     * the inventory ui, if button on inventory
     * otherwise null
     */
    private InventoryUI inventory;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        if(shop != null) {

        }
        else if(inventory != null) {

        }
    }

    /**
     * constructor
     * @param ui the shop ui this is on, or null
     * @param inventory the inventory ui this is on, or null
     */
    public InventoryCloseButton(float x, float y, float width, float height, ShopUI ui, InventoryUI inventory) {
        super(new Texture("close_on.png"), x, y, width, height);
        shop = ui;
        this.inventory = inventory;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
