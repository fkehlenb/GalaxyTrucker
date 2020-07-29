package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory.InventoryUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Map.MapUI;

/**
 * used to close the inventory
 */
public class InventoryCloseButton extends ImButton {

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
            shop.disposeShopUI();
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
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param shop ui this button could be on
     * @param map ui this button could be on
     * @param inventory ui this button could be on
     */
    public InventoryCloseButton(float x, float y, float width, float height, ShopUI shop, InventoryUI inventory, MapUI map) {
        super(new Texture("close_on.png"), x, y, width, height);
        this.shop = shop;
        this.inventory = inventory;
        this.map = map;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
