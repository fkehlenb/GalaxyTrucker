package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class CurrentShopUI {

    /**
     * the main class extending game
     */
    @NonNull
    private Main main;

    /**
     * the Stage for rendering
     */
    @NonNull
    private Stage stage;

    /**
     * the screen all of this is on, for the buy/sell methods that need to communicate with the controllers
     */
    @NonNull
    private GamePlay game;

    /**
     * the Trader who owns the Shop
     */
    @NonNull
    private Trader trader;

    /**
     * the shop which is loaded
     */
    @NonNull
    private ShopUI shop;

    /**
     * base positions for rendering
     */
    @NonNull
    private float baseX, baseY;
    

    public abstract void render();
    public abstract void dispose();
}
