package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class ShopResource extends CurrentShopUI {


    /**
     * the items that can be sold
     */
    //private List<BuyableElements> buyableElements;


    public ShopResource(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI){
        super(main, stage, game,trader,shopUI);

        //fuel
       // elements.add(new ShopElement(main, stage, new Texture("fuel.png"), 0, 0, this, null, null, trader.getFuelStock(), "fuel"));
        //hp
        //elements.add(new ShopElement(main, stage, new Texture("hp.png"), 0, 0, this, null, null, trader.getHpStock(), "hp"));
        //missiles/rockets
        // elements.add(new ShopElement(main, stage, new Texture("missiles.png"), 0, 0, this, null, null, trader.getMissileStock(), "missiles"));
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
