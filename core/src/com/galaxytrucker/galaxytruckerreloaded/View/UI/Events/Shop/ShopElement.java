package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons.ShopBuyButton;

public class ShopElement {

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the button with which to buy something
     */
    private ShopBuyButton button;

    /**
     * the texture of this element
     */
    private Texture texture;

    /**
     * the position on the screen
     */
    private float x, y;

    /**
     * the weapon, if this is a weapon
     */
    private Weapon weapon;

    /**
     * the crew member, if this is a crew member
     */
    private Crew crew;

    /**
     * the system, if this is a system
     */
    private System system;

    /**
     * the amount, if this is fuel, hp or missiles
     */
    private int amount;

    /**
     * the name (fuel, hp, or missiles) if this is one of those
     */
    private ShopElementType type;

    /**
     * the price of the object
     */
    private int price;

    /**
     * the shop ui this element is on
     */
    private ShopUI shop;

    /**
     * constructor
     * @param main the main game class
     * @param texture the texture for this element
     */
    public ShopElement(Main main, Stage stage, Texture texture, float x, float y, ShopUI shop, Weapon weapon, Crew crew, System system, int amount, ShopElementType type) {
        this.main = main;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.shop = shop;
        this.weapon = weapon;
        this.crew = crew;
        this.amount = amount;
        this.type = type;


        button = new ShopBuyButton(texture, main.WIDTH/2, main.HEIGHT/2, main.WIDTH/6.4f, main.WIDTH/38.4f, this); //TODO whxy
        stage.addActor(button);
        switch (type){
            case HP:
                price = 2;
                break;
            case FUEL:
                price = 3;
                break;
            case MISSILES:
                price = 6;
                break;
            case WEAPON:
                price = weapon.getWeaponPrice();
                break;
            case CREW:
                price = crew.getPrice();
                break;
            case SYSTEM:
                //TODO System.getPrice
                break;
            case UPGRADES:
                //TODO lololol idk
                break;
        }
    }

    /**
     * render no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(texture, x, y, 10, 10); //TODO whxy
        main.batch.end();
    }

    /**
     * dispose of this ui
     */
    public void dispose() {
        texture.dispose();
        button.remove();
    }

    /**
     * button was clicked, this item is to be bought
     */
    public void buy() {
        boolean success = false;
        if(weapon != null) {
            success = shop.buyWeapon(weapon);
        }
        else if(crew != null) {
            success = shop.buyCrew(crew);
        }
        else if(type != null) {
            switch (type) {
                case MISSILES:
                    success = shop.buyMissiles();
                    break;
                case FUEL:
                    success = shop.buyFuel();
                    break;
                case HP:
                    success = shop.buyHP(amount);
                    break;
            }
        }
        if(success) {
            dispose();
        }
    }
}
