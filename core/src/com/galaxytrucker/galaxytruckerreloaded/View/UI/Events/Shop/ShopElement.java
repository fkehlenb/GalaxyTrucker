package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.UserService;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons.ShopBuyButton;
import lombok.Getter;
import lombok.Setter;

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
    @Getter
    @Setter
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

    private GlyphLayout stockTag;

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
        this.system = system;


        button = new ShopBuyButton(texture, x, y+texture.getHeight()/2, texture.getWidth(), texture.getHeight(), this); //TODO whxy
        stage.addActor(button);
        switch (type){
            case HP:
                price = 2*amount;
                stockTag = new GlyphLayout();
                stockTag.setText(font, Integer.toString(amount));
                break;
            case FUEL:
                price = 3;
                stockTag = new GlyphLayout();
                stockTag.setText(font, Integer.toString(amount));
                break;
            case MISSILES:
                price = 6;
                stockTag = new GlyphLayout();
                stockTag.setText(font, Integer.toString(amount));
                break;
            case WEAPON:
                price = weapon.getWeaponPrice();
                for (int lvl=1;  lvl < weapon.getWeaponLevel(); lvl++)
                {
                    price += weapon.getPrice().get(lvl);
                }
                stockTag = new GlyphLayout();
                stockTag.setText(font, "lvl "+amount);
                break;
            case CREW:
                price = crew.getPrice();
                break;
            case SYSTEM:
                price = 60;
                break;
            case UPGRADES:
                setSystemPrice();
                break;
            case CREWUPGRADES:
                price = 20;
                break;
        }
        priceTag.setText(font, Integer.toString(price)+" coins");

    }

    /**
     * render no stage stuff
     */
    public void render() {
        main.batch.begin();
        //main.batch.draw(texture, x, y, 300, 300); //TODO whxy
        font.draw(main.batch, priceTag, x + main.WIDTH/12, y + texture.getHeight() + priceTag.height/2);

        if (stockTag!=null)
        {
            if (type != ShopElementType.WEAPON)
            {stockTag.setText(font, Integer.toString(amount));}
            font.draw(main.batch, stockTag, x - stockTag.width/2 + main.WIDTH/24, y  + texture.getHeight() + stockTag.height/2);
        }
        //main.batch.draw(new Texture("yes.png"),x,y);
        main.batch.end();
        stage.draw();
    }

    /**
     * dispose of this ui
     */
    public void dispose() {
        //texture.dispose();
        priceTag.setText(font, "");
        if (stockTag != null)
        {
            //stockTag.setText(font, "");
            stockTag=null;
        }
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
                    if(success) {
                        dispose();
                    }
                    break;
                case CREW:
                    success = shop.buyCrew(crew);
                    if(success) {
                        dispose();
                    }
                    break;
                case MISSILES:
                    success = shop.buyMissiles();
                    if(success) {
                        amount -= 1;
                        if (amount==0) {dispose();}
                    }
                    break;
                case FUEL:
                    success = shop.buyFuel();
                    if(success) {
                        amount -= 1;
                        if (amount==0) {dispose();}
                    }
                    break;
                case HP:
                    success = shop.buyHP(amount);
                    if(success) {
                        dispose();
                    }
                    break;
                case SYSTEM:
                    success = shop.buySystem(system.getSystemType());
                    if(success) {
                        dispose();
                    }
                    break;
                case UPGRADES:
                    success = shop.upgradeSystem(system.getSystemType());
                    if(success) {
                        updateSystemPrice();
                    }
                    break;
                case CREWUPGRADES:
                    success = shop.upgradeCrew(crew);
                    break;
            }
    }

    private void updateSystemPrice() {
        Ship s = ClientControllerCommunicator.getInstance(null).getClientShip();
        for(Room r : s.getSystems()) {
            if(r.getId() == system.getId()) {
                system = (System) r;
                setSystemPrice();
                priceTag.setText(font, Integer.toString(price)+" coins");
                break;
            }
        }
    }

    /**
     * set the system price to be displayed
     */
    private void setSystemPrice() {
        switch(system.getSystemType()) {
            case WEAPON_SYSTEM:
                switch(system.getMaxEnergy()) {
                    case 1: price = 50; break;
                    case 2: price = 25; break;
                    case 3: price = 40; break;
                    case 4: price = 60; break;
                    case 5: price = 80; break;
                    case 6: price = 90; break;
                    case 7: price = 100; break;
                }
                break;
            case ENGINE:
                switch(system.getMaxEnergy()) {
                    case 1: price = 15; break;
                    case 2: price = 25; break;
                    case 3: price = 40; break;
                    case 4: price = 60; break;
                    case 5: price = 80; break;
                    case 6: price = 100; break;
                    case 7: price = 125; break;
                }
                break;
            case SHIELDS:
                switch(system.getMaxEnergy()) {
                    case 1: price = 0; break;
                    case 2: price = 20; break;
                    case 3: price = 25; break;
                    case 4: price = 30; break;
                    case 5: price = 40; break;
                    case 6: price = 55; break;
                    case 7: price = 75; break;
                }
                break;
            case O2:
                switch(system.getMaxEnergy()) {
                    case 1: price = 30; break;
                    case 2: price = 35; break;
                }
                break;
            case MEDBAY:
                switch(system.getMaxEnergy()) {
                    case 1: price = 20; break;
                    case 2: price = 30; break;
                }
                break;
            case CAMERAS:
                switch(system.getMaxEnergy()) {
                    case 1: price = 35; break;
                    case 2: price = 40; break;
                }
                break;
            case COCKPIT:
                switch(system.getMaxEnergy()) {
                    case 1: price = 15; break;
                    case 2: price = 25; break;
                }
                break;
        };
    }
}
