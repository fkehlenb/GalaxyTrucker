package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.ArrayList;
import java.util.List;

public class ShopWeapon extends CurrentShopUI {

    List<ShopElement> elements;

    public ShopWeapon(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI, float x, float y){
        super(main, stage, game,trader,shopUI, x, y);
        elements = new ArrayList<>();
        //weapon stock
        float dist = main.HEIGHT/13.5f;
        int i = 0;
        for(Weapon w : trader.getWeaponStock()) {
            String name = w.getWeaponType().toString();
            elements.add(new ShopElement(main, stage, new Texture("shipsys/weapon_system/"+name.toLowerCase()+".png"), baseX, baseY+dist*i, shopUI, w, null, null, 0, ShopElementType.WEAPON));
            i++;
        }
    }

    @Override
    public void render() {
        for (ShopElement e: elements) {
            e.render();
        }
    }

    @Override
    public void dispose() {
        for (ShopElement e: elements) {
            e.dispose();
        }
    }
}

