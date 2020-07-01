package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
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

    /**
     * the items that can be sold
     */
    private List<ShopSellElement> sellElements;

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the screen all of this is on, for the buy/sell methods that need to communicate with the controllers
     */
    private GamePlay game;

    /**
     * constructor
     * @param main the main class
     */
    public ShopUI(Main main, Stage stage, GamePlay game, Trader trader, List<Weapon> shipWeapons, int shipMissiles) {
        this.main = main;
        this.game = game;

        // add all the items the trader has to offer to the ui
        elements = new LinkedList<>();
        //crew stock
        for(Crew c : trader.getCrewStock()) {
            Texture t;
            if(c.getName().equals("ana")) {
                t = new Texture("crew/anaerobic.png");
            }
            else if(c.getName().equals("battle")) {
                t = new Texture("crew/battle.png");
            }
            else {
                t = new Texture("crew/energy.png"); //TODO wie sieht das mit namen aus?
            }
            elements.add(new ShopElement(main, stage, t, 0, 0, this, null, c, 0, null));
        }
        //weapon stock
        for(Weapon w : trader.getWeaponStock()) {
            elements.add(new ShopElement(main, stage, new Texture("laser.png"), 0, 0, this, w, null, 0, null));
        }
        //fuel
        elements.add(new ShopElement(main, stage, new Texture("fuel.png"), 0, 0, this, null, null, trader.getFuelStock(), "fuel"));
        //hp
        elements.add(new ShopElement(main, stage, new Texture("hp.png"), 0, 0, this, null, null, trader.getHpStock(), "hp"));
        //missiles/rockets
        elements.add(new ShopElement(main, stage, new Texture("missiles.png"), 0, 0, this, null, null, trader.getMissileStock(), "missiles"));

        //add all the items that can be sold TODO geldanzeige immer ändern
        sellElements = new LinkedList<>();
        //missiles
        sellElements.add(new ShopSellElement(main, stage, new Texture("missiles.png"), 0, 0, this, null, shipMissiles));
        //weapons
        for(Weapon w : shipWeapons) {
            sellElements.add(new ShopSellElement(main, stage, new Texture("laser.png"), 0, 0, this, w, 0));
        }

        closeButton = new InventoryCloseButton(0, 0, 10, 10, this, null);
        stage.addActor(closeButton);

        background = new Texture("shop/storeback.png");
    }

    /**
     * buy a weapon from the trader
     * @param weapon the weapon
     */
    boolean buyWeapon(Weapon weapon) {
        return game.buyWeapon(weapon);
    }

    /**
     * buy a crew member from the trader
     * @param crew the crew member
     */
    boolean buyCrew(Crew crew) {
        return game.buyCrew(crew);
    }

    /**
     * buy fuel from the trader
     * @param amount the amount of fuel
     */
    boolean buyFuel(int amount) {
        return game.buyFuel(amount);
    }

    /**
     * buy missiles from the trader
     * @param amount the amount of missiles
     */
    boolean buyMissiles(int amount) {
        return game.buyMissiles(amount);
    }

    /**
     * buy hp from the trader
     * @param amount the amount of hp
     */
    boolean buyHp(int amount) {
        return game.buyHp(amount);
    }

    /**
     * sell missiles to the trader
     * @param amount the amount
     * @return successful?
     */
    boolean sellMissiles(int amount) {
        return game.sellMissiles(amount);
    }

    /**
     * sell weapon to trader
     * @param weapon the weapon
     */
    boolean sellWeapon(Weapon weapon) {
        return game.sellWeapon(weapon);
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
     * remove a buyable element
     */
    public void removeBuyElement(ShopElement e) {
        elements.remove(e); //TODO add to the list of sellable, if weapon/missiles
    }

    /**
     * remove a sellable element
     * @param e the element
     */
    public void removeSellElement(ShopSellElement e) {
        sellElements.remove(e); //TODO add to the list of buyable items
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
