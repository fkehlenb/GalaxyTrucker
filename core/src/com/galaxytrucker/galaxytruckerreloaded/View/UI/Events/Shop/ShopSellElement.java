package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons.ShopSellButton;
import lombok.Getter;

@Getter
public class ShopSellElement {

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the button with which to buy something
     */
    private ShopSellButton button;

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
     * the amount, if this is fuel, hp or missiles
     */
    private Crew crew;

    /**
     * the shop ui this element is on
     */
    private ShopUI shop;

    private GlyphLayout priceTag = new GlyphLayout();

    private GlyphLayout stockTag;

    private BitmapFont font;

    private Stage stage;

    /**
     * the price of the object
     */
    private int price;



    /**
     * the constructor
     * @param main the main class
     * @param stage the stage for buttons
     * @param texture the texture for this element
     * @param x the position
     * @param y the position
     * @param shop the shop this is on
     * @param weapon the weapon if this represents a weapon
     * @param crew the amount, if this represents missiles
     */
    public ShopSellElement(Main main, Stage stage, Texture texture, float x, float y, ShopUI shop, Weapon weapon, Crew crew) {
        this.main = main;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.shop = shop;
        this.weapon = weapon;
        this.crew = crew;
        this.texture = texture;
        this.stage = stage;
        this.font = shop.getFont();

        button = new ShopSellButton(texture, x, y+texture.getHeight()/2, texture.getWidth(), texture.getHeight(), shop, this);
        stage.addActor(button);
        price = weapon.getWeaponPrice();
            for (int lvl=1;  lvl < weapon.getWeaponLevel()-1; lvl++)
            {

                price += 1;
                //TODO: Weaponprices hardcoden
                price += weapon.getPrice().get(lvl);
            }
        priceTag.setText(font, Integer.toString(price)+" coins");
    }

    /**
     * dispose of the shop sell element
     */
    public void disposeShopSellElement() {
        //shop.removeSellElement(this);
        //texture.dispose();
        priceTag.setText(font,"");
        button.remove();
    }

    public void render() {
        main.batch.begin();
        //main.batch.draw(texture, x, y, 300, 300); //TODO whxy
        font.draw(main.batch, priceTag, x + main.WIDTH/12 , y + texture.getHeight()- priceTag.height/2);
        priceTag.setText(font, Integer.toString(price)+" coins");
        
        main.batch.end();
        stage.draw();
    }

    /**
     * buy something
     */
    public void sell() {
        boolean success = false;
        if(weapon != null) {
            success = sellWeapon();
        }
        if(success) {
            disposeShopSellElement();
        }
    }

    /**
     * sell a weapon
     */
    private boolean sellWeapon() {
        return shop.sellWeapon(weapon, this);
    }

}
