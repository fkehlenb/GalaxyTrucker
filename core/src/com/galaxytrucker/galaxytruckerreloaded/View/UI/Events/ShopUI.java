package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.InventoryCloseButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShopBuyButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShopSellButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;


/**
 * UI for the case that an event is a shop opportunity
 *
 * called in the method openShop in eventGUI
 *
 */
public class ShopUI {

    private ShopCrew shopCrew;
    private ShopCrewButton shopCrewButton;

    private ShopWeapon shopWeapon;
    private ShopWeaponButton shopWeaponButton;

    private  ShopSystem shopSystem;
    private ShopSystemButton shopSystemButton;

    private ShopUpgrade shopUpgrade;
    private ShopUpgradeButton shopUpgradeButton;

    private  ShopSell shopSell;
    private ShopSellButton shopSellButton;

    private ShopResource shopResource;
    private ShopResourceButton shopResourceButton;

    @Getter
    @Setter
    private CurrentShopUI current;

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

    private Stage stage;

    private Trader trader;

    /**
     * constructor
     * @param main the main class
     */
    public ShopUI(Main main, Stage stage, GamePlay game, Trader trader, List<Weapon> shipWeapons, int shipMissiles) {
        this.main = main;
        this.game = game;

        //shopCrew = new ShopCrew(main, stage, game, trader, this);
        //shopSell = new ShopSell(main, stage, game, trader, this);
        //shopSystem = new ShopSystem(main, stage, game, trader, this);
        //shopUpgrade = new ShopUpgrade(main, stage, game, trader, this);
        //shopWeapon = new ShopWeapon(main, stage, game, trader, this);
        //shopResource = new ShopResource(main, stage, game, trader, this);

        shopCrewButton =new ShopCrewButton(0, 0, 10, 10, this, null, null);
        shopWeaponButton = new ShopWeaponButton(0, 0, 10, 10, this, null, null);
        shopResourceButton = new ShopResourceButton(0, 0, 10, 10, this, null, null);
        shopSellButton = new ShopSellButton(0, 0, 10, 10, this, null, null);
        shopSystemButton = new ShopSystemButton(0, 0, 10, 10, this, null, null);
        shopUpgradeButton = new ShopUpgradeButton(0, 0, 10, 10, this, null, null);

        closeButton = new InventoryCloseButton(0, 0, 10, 10, this, null, null);
        stage.addActor(closeButton);
        stage.addActor(shopSellButton);
        stage.addActor(shopCrewButton);
        stage.addActor(shopWeaponButton);
        stage.addActor(shopResourceButton);
        stage.addActor(shopSystemButton);
        stage.addActor(shopUpgradeButton);

        background = new Texture("shop/storeback.png");
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

    public void openSellUI(){
        new ShopSell(main, stage, game, trader, this);
    }

    public void openShopCrewUI(){
        new ShopCrew(main, stage, game, trader, this);
    }

    public void openShopResourceUI(){
        new ShopResource(main, stage, game, trader, this);
    }

    public void openShopSystemUI(){
        new ShopSystem(main, stage, game, trader, this);
    }

    public void openShopUpgrade(){
        new ShopUpgrade(main, stage, game, trader, this);
    }

    public void openShopWeapon(){
        new ShopWeapon(main, stage, game, trader, this);
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
