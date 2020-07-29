package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

    private Stage stage;

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

    private GlyphLayout priceTag = new GlyphLayout();

    private BitmapFont font;
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
        this.stage = stage;
        this.x = x;
        this.y = y;
        this.shop = shop;
        this.weapon = weapon;
        this.crew = crew;
        this.amount = amount;
        this.type = type;
        this.font = shop.getFont();


        button = new ShopBuyButton(texture, x, y-texture.getHeight()/2, main.WIDTH/6.4f, main.WIDTH/38.4f, this); //TODO whxy
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
                for (int lvl=1;  lvl < weapon.getWeaponLevel(); lvl++)
                {
                    price += weapon.getPrice().get(lvl);
                }
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
        priceTag.setText(font, Integer.toString(price));

    }

    /**
     * render no stage stuff
     */
    public void render() {
        main.batch.begin();
        //main.batch.draw(texture, x, y, 300, 300); //TODO whxy
        font.draw(main.batch, priceTag, x + main.WIDTH/32 , y - priceTag.height/2);
        main.batch.end();
        stage.draw();
    }

    /**
     * dispose of this ui
     */
    public void dispose() {
        //texture.dispose();
        priceTag.setText(font, "");
        button.remove();
    }

    /**
     * button was clicked, this item is to be bought
     */
    public void buy() {
        boolean success = false;
            switch (type) {
                case WEAPON:
                    success = shop.buyWeapon(weapon);
                    break;
                case CREW:
                    success = shop.buyCrew(crew);
                    break;
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
        if(success) {
            dispose();
        }
    }
}
