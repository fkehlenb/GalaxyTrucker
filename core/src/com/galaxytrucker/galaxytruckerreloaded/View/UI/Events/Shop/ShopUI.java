package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.InventoryCloseButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons.*;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * UI for the case that an event is a shop opportunity
 *
 * called in the method openShop in eventGUI
 *
 */
public class ShopUI {

    ShopUIButton ShopTab;

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

        ShopUIButton shopWeaponButton = new ShopUIButton(new Texture("shop/weaponTab.png"),0, 0, 10, 10, this, ShopButtonType.WEAPON);
        ShopUIButton shopResourceButton = new ShopUIButton(new Texture("shop/resourcesTab.png"),0, 0, 10, 10, this, ShopButtonType.RESOURCE);
        ShopUIButton shopCrewButton =new ShopUIButton(new Texture("shop/crewTab.png"),0, 0, 10, 10, this, ShopButtonType.CREW);
        ShopUIButton shopSystemButton = new ShopUIButton(new Texture("shop/systemTab.png"),0, 0, 10, 10, this,  ShopButtonType.SYSTEM);
        ShopUIButton shopUpgradeButton = new ShopUIButton(new Texture("shop/lvlTab.png"),0, 0, 10, 10, this,  ShopButtonType.UPGRADES);;
        ShopUIButton shopSellButton = new ShopUIButton(new Texture("shop/sellTab"), 0, 0, 10, 10, this,  ShopButtonType.SELL);

        closeButton = new InventoryCloseButton(0, 0, 10, 10, this, null, null);
        stage.addActor(closeButton);
        stage.addActor(shopWeaponButton);
        stage.addActor(shopResourceButton);
        stage.addActor(shopCrewButton);
        stage.addActor(shopSystemButton);
        stage.addActor(shopUpgradeButton);
        stage.addActor(shopSellButton);

        background = new Texture("shop/storeback.png");
    }

    /**
     * buy a weapon from the trader
     * @param weapon the weapon
     */
    boolean buyWeapon(Weapon weapon) {
        //TODO Controllerklassen verbinden
        return game.buyWeapon(weapon);
    }

    /**
     * sell weapon to trader
     * @param weapon the weapon
     */
    boolean sellWeapon(Weapon weapon) {
        return game.sellWeapon(weapon);
    }

    /**
     *  Hire Crew from Trader
     * @param crew the crewmember
     * @return success of transaction
     */
    boolean buyCrew(Crew crew){return game.buyCrew(crew);}

    //TODO SellCrew???
    boolean sellCrew(Crew crew){
        return false;
        //return game.sellCrew(crew);
    }

    /**
     * Buy a Unit of Fuel from the Trader
     * @return success of transaction
     */
    boolean buyFuel(){return game.buyFuel(1);}

    /**
     * Buy a missile from the Trader
     * @return success of transaction
     */
    boolean buyMissiles(){return game.buyMissiles(1);}

    /**
     * Buy some repairs from the Trader
     * @return success of transaction
     */
    boolean buyHP(int amount){return game.buyHp(amount);}

    /**
     * Dispose shop ui
     */
    public void disposeShopUI() {
        for(ShopElement e : elements) {
            e.disposeShopElement();
        }
        current.dispose();
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
    public void render(){
        main.batch.begin();
        main.batch.draw(background, 0, 0, 10, 10); //TODO whxy
        main.batch.end();
        for(ShopElement e : elements) {
            e.render();
        }
    }

    //TODO woher wissen wir was wir verkaufen?
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
