package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.InventoryCloseButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShopBuyButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.LinkedList;
import java.util.List;


/**
 * UI for the case that an event is a shop opportunity
 *
 * called in the method openShop in eventGUI
 *
 */
public class ShopUI {

    /**
     * to close the shop
     */
    private InventoryCloseButton closeButton;

    /**
     * the background texture
     */
    private Texture background;

    /**
     * the items that can be bought
     */
    private List<ShopElement> elements;

    private Main main;

    private Stage stage;

    private GamePlay game;

    /**
     * constructor
     * @param main the main class
     *             TODO wie werden hier die objekte die zum verkauf stehen übergeben?
     */
    public ShopUI(Main main, Stage stage, GamePlay game) {
        this.main = main;
        this.stage = stage;
        this.game = game;

        elements = new LinkedList<>();

        closeButton = new InventoryCloseButton(0, 0, 10, 10, this, null);
        stage.addActor(closeButton);

        background = new Texture("shop/storeback.png");
    }

    /**
     * an item is bought
     * called by button
     * @param item the item (index in the list)
     */
    public void buy(int item) {
        game.buy(item);
    }

    /**
     * Dispose shop ui
     */
    public void disposeShopUI() {
        for(ShopElement e : elements) {
            e.disposeShopElement();
        }
        closeButton.remove();
        background.dispose();
        game.deleteShop();
    }

    /**
     * render without stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(background, 0, 0, 10, 10); //TODO whxy
        main.batch.end();
        for(ShopElement e : elements) {
            e.render();
        }
    }

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
}
