package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

public class ShopResource extends CurrentShopUI {

    /**
     * the items that can be sold
     */
    private List<ShopElement> elements;

    @Builder
    public ShopResource(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI, float x, float y){
        super(main, stage, game,trader,shopUI, x, y);
        elements = new ArrayList<>();
        float dist = main.HEIGHT/27;
        //TODO add icons
        //fuel
        elements.add(new ShopElement(main, stage, new Texture("gameuis/top_fuel.png"), baseX, baseY+dist, shopUI, null, null, null, trader.getFuelStock(), ShopElementType.FUEL));
        //hp
        elements.add(new ShopElement(main, stage, new Texture("icon_fuel.png"), baseX, baseY+dist*2, shopUI, null, null, null, trader.getHpStock(), ShopElementType.HP));
        //missiles/rockets
        elements.add(new ShopElement(main, stage, new Texture("gameuis/top_missile.png"), baseX, baseY, shopUI, null, null, null, trader.getMissileStock(), ShopElementType.MISSILES));
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
