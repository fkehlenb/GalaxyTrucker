package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class ShopWeapon extends CurrentShopUI {

    public ShopWeapon(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI){
        super(main, stage, game,trader,shopUI);
        //weapon stock
        for(Weapon w : trader.getWeaponStock()) {
            elements.add(new ShopElement(main, stage, new Texture("laser.png"), 0, 0, this, w, null, 0, null));
        }
    }

    /**
     * buy a weapon from the trader
     * @param weapon the weapon
     */
    boolean buyWeapon(Weapon weapon) {
        return game.buyWeapon(weapon);
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}

