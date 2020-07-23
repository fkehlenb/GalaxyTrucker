package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public abstract class CurrentShopUI {

    /**
     * the main class extending game
     */
    private Main main;

    /**
     *
     */
    private Stage stage;

    /**
     * the screen all of this is on, for the buy/sell methods that need to communicate with the controllers
     */
    private GamePlay game;

    /**
     *
     */
    private Trader trader;

    /**
     *
     */
    private ShopUI shop;

    public CurrentShopUI(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shop){
        this.main = main;
        this.stage = stage;
        this.game = game;
        this.trader = trader;
        this.shop = shop;


    }

    public abstract void render();
    public abstract void dispose();
}
