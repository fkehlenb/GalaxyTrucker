package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.LinkedList;

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
private Gameplay game;


public class ShopSell {

    public ShopSell(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI){
        this.main = main;
        this.game = game;

        //add all the items that can be sold TODO geldanzeige immer ändern
        sellElements = new LinkedList<>();
        //missiles
        sellElements.add(new ShopSellElement(main, stage, new Texture("missiles.png"), 0, 0, this, null, shipMissiles));
        //weapons
        for(Weapon w : shipWeapons) {
            sellElements.add(new ShopSellElement(main, stage, new Texture("laser.png"), 0, 0, this, w, 0));
        }
    }
}
