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

    private float x, y;


    /**
     * constructor
     * @param main the main class
     */
    public ShopUI(Main main, Stage stage, GamePlay game, Trader trader, List<Weapon> shipWeapons, int shipMissiles) {
        this.main = main;
        this.game = game;
        this.stage=stage;
        this.trader=trader;

        background = new Texture("shop/storeback.png");
        x = main.WIDTH/2 - background.getWidth()/2;
        y = main.HEIGHT/2 - background.getHeight()/2;

        //shopCrew = new ShopCrew(main, stage, game, trader, this);
        //shopSell = new ShopSell(main, stage, game, trader, this);
        //shopSystem = new ShopSystem(main, stage, game, trader, this);
        //shopUpgrade = new ShopUpgrade(main, stage, game, trader, this);
        //shopWeapon = new ShopWeapon(main, stage, game, trader, this);
        //shopResource = new ShopResource(main, stage, game, trader, this);

        float xb = x+main.WIDTH/128f;
        float yb = y+main.HEIGHT/90f;
        float ydist = main.HEIGHT/15.652f;

        ShopUIButton shopWeaponButton = new ShopUIButton(new Texture("shop/weaponTab.png"),xb, yb, main.WIDTH/30.476f, main.HEIGHT/16.875f, this, ShopButtonType.WEAPON);
        ShopUIButton shopResourceButton = new ShopUIButton(new Texture("shop/resourcesTab.png"),xb, yb+ydist, main.WIDTH/30.476f, main.HEIGHT/16.875f, this, ShopButtonType.RESOURCE);
        ShopUIButton shopCrewButton =new ShopUIButton(new Texture("shop/crewTab.png"),xb, yb+2*ydist, main.WIDTH/30.476f, main.HEIGHT/16.875f, this, ShopButtonType.CREW);
        ShopUIButton shopSystemButton = new ShopUIButton(new Texture("shop/systemTab.png"),xb, yb+3*ydist, main.WIDTH/30.476f, main.HEIGHT/16.875f, this,  ShopButtonType.SYSTEM);
        ShopUIButton shopUpgradeButton = new ShopUIButton(new Texture("shop/lvlTab.png"),xb, yb+4*ydist, main.WIDTH/30.476f, main.HEIGHT/16.875f, this,  ShopButtonType.UPGRADES);;
        ShopUIButton shopSellButton = new ShopUIButton(new Texture("shop/sellTab.png"), xb, yb+5*ydist, main.WIDTH/30.476f, main.HEIGHT/16.875f, this,  ShopButtonType.SELL);

        //closeButton = new InventoryCloseButton(x+Main.WIDTH/6.4f, y+Main.HEIGHT/67.5f, Main.WIDTH/7.742f, Main.HEIGHT/21.6f, this, null, null);
        closeButton = new InventoryCloseButton(x+Main.WIDTH/7f, y+Main.HEIGHT/67.5f, Main.WIDTH/7.742f, Main.HEIGHT/21.6f, this, null, null);
        stage.addActor(closeButton);
        stage.addActor(shopWeaponButton);
        stage.addActor(shopResourceButton);
        stage.addActor(shopCrewButton);
        stage.addActor(shopSystemButton);
        stage.addActor(shopUpgradeButton);
        stage.addActor(shopSellButton);

    }

    /**
     * render without stage stuff
     */
    public void render(){
        main.batch.begin();
        main.batch.draw(background, x, y,Main.WIDTH/3.195f, Main.HEIGHT/2.293f); //TODO whxy
        main.batch.end();
        /*for(ShopElement e : elements) {
            e.render();
        }*/
    }

    /**
     * Dispose shop ui
     */
    public void disposeShopUI() {
        if (current != null) {
            current.dispose();
        }
        closeButton.remove();
        background.dispose();
        game.deleteShop();
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
     * remove a buyable element
     */
    public void removeBuyElement(ShopElement e) {
        elements.remove(e); //TODO add to the list of sellable, if weapon/missiles
    }

    public void openShopSellUI(){
        current = new ShopSell(main, stage, game, trader, this);
    }

    public void openShopCrewUI(){
        current = new ShopCrew(main, stage, game, trader, this);
    }

    public void openShopResourceUI(){
        current = new ShopResource(main, stage, game, trader, this);
    }

    public void openShopSystemUI(){
        current = new ShopSystem(main, stage, game, trader, this);
    }

    public void openShopUpgradeUI(){
        current = new ShopUpgrade(main, stage, game, trader, this);
    }

    public void openShopWeaponUI(){
        current = new ShopWeapon(main, stage, game, trader, this);
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
