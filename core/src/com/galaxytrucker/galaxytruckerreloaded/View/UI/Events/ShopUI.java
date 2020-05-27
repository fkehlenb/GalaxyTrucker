package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.InventoryCloseButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShopBuyButton;


/**
 * UI for the case that an event is a shop opportunity
 *
 * called in the method openShop in eventGUI
 *
 */
public class ShopUI {

    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * to close the shop
     */
    private InventoryCloseButton closeButton;

    /**
     * list of textures to display stuff. each texture gets one button
     */
    private List<Texture> textures;

    /**
     * buttons to buy stuff, set up in setup
     */
    private List<ShopBuyButton> buttons;

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Show the shop ui
     */
    public void showShopUI() {
    }

    /**
     * Hide the shop ui
     */
    public void hideShopUI() {
    }

    /**
     * Dispose shop ui
     */
    public void disposeShopUI() {
    }

    /**
     * an item is bought
     * called by button
     * @param item the item (index in the list)
     */
    public void buy(int item) {

    }


    /**
     * constructor
     * @param main the main class
     *             TODO wie werden hier die objekte die zum verkauf stehen übergeben?
     */
    public ShopUI(Main main) {

    }
}
