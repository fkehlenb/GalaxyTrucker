package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import lombok.Builder;

import java.util.List;

public class ShopResource extends CurrentShopUI {

    private Main main;

    /**
     * the items that can be sold
     */
    private List<ShopElement> elements;

    @Builder
    public ShopResource(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI){
        super(main, stage, game,trader,shopUI);

        //fuel
        elements.add(new ShopElement(main, stage, new Texture("fuel.png"), main.WIDTH/2, main.HEIGHT/2, shopUI, null, null, trader.getFuelStock(), "fuel"));
        //hp
        elements.add(new ShopElement(main, stage, new Texture("hp.png"), main.WIDTH/2, main.HEIGHT/2-30, shopUI, null, null, trader.getHpStock(), "hp"));
        //missiles/rockets
        elements.add(new ShopElement(main, stage, new Texture("missiles.png"), main.WIDTH/2, main.HEIGHT-30, shopUI, null, null, trader.getMissileStock(), "missiles"));
    }

    @Override
    public void render() {
        for (ShopElement e: elements) {
            e.render();
        }
    }

    @Override
    public void dispose() {

    }
}
