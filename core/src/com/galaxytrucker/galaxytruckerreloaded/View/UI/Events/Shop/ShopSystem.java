package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.ArrayList;
import java.util.List;

public class ShopSystem extends CurrentShopUI {

    /**
     * the items that can be sold
     */
    private List<ShopElement> elements;

    public ShopSystem(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI, float x, float y){
        super(main, stage, game,trader,shopUI, x,y);
        elements = new ArrayList<>();

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
