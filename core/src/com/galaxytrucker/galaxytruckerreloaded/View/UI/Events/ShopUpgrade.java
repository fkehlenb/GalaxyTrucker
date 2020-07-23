package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShopBuyButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class ShopUpgrade extends CurrentShopUI {

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
     * the amount, if this is fuel, hp or missiles
     */
    private int amount;

    /**
     * the name (fuel, hp, or missiles) if this is one of those
     */
    private String type;

    public ShopUpgrade(Main main, Stage stage, GamePlay game, Trader trader, ShopUI shopUI){
        super(main, stage, game,trader,shopUI);

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }

    /**
     * the shop ui this element is on
     */
}
