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
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Map.MapUI;

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
     * map ui, if Button on map
     * otherwise null
     */
    private MapUI map;

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
            inventory.disposeInventoryUI();
        }

        else if (map != null) {
            map.disposeMapUI();
        }
    }

    /**
     * constructor
     * @param ui the shop ui this is on, or null
     * @param inventory the inventory ui this is on, or null
     * @param map the map ui this is on, or null
     */
    public InventoryCloseButton(float x, float y, float width, float height, ShopUI ui, InventoryUI inventory, MapUI map) {
        super(new Texture("close_on.png"), x, y, width, height);
        shop = ui;
        this.inventory = inventory;
        this.map = map;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
