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
     * the amount, if this is fuel, hp or missiles
     */
    private int amount;

    /**
     * the name (fuel, hp, or missiles) if this is one of those
     */
    private String type;

    /**
     * the shop ui this element is on
     */
    private ShopUI shop;

    /**
     * constructor
     * @param main the main game class
     * @param texture the texture for this element
     */
    public ShopElement(Main main, Stage stage, Texture texture, float x, float y, ShopUI shop, Weapon weapon, Crew crew, int amount, String type) {
        this.main = main;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.shop = shop;
        this.weapon = weapon;
        this.crew = crew;
        this.amount = amount;
        this.type = type;

        button = new ShopBuyButton(0, 0, 10, 10, this); //TODO whxy
        stage.addActor(button);
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
    public void disposeShopElement() {
        texture.dispose();
        button.remove();
        shop.removeBuyElement(this);
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
                case "missiles":
                    success = shop.buyMissiles();
                    break;
                case "fuel":
                    success = shop.buyFuel();
                    break;
                case "hp":
                    success = shop.buyHP(amount);
                    break;
            }
        }
        if(success) {
            disposeShopElement();
        }
    }
}