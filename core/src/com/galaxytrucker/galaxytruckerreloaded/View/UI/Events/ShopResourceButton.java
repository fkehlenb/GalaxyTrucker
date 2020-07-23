package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class ShopResourceButton extends ImButton {

    private ShopResource shopResource;

    private ShopUI shop;
    private Main main;
    private Stage stage;
    private GamePlay game;
    private Trader trader;

    public ShopResourceButton(int i, int i1, int i2, int i3, ShopUI shopUI, Object o, Object o1) {


    }

    /**
     * buy fuel from the trader
     * @param amount the amount of fuel
     */
    boolean buyFuel(int amount) {
        return game.buyFuel(amount);
    }

    /**
     * buy missiles from the trader
     * @param amount the amount of missiles
     */
    boolean buyMissiles(int amount) {
        return game.buyMissiles(amount);
    }

    /**
     * buy hp from the trader
     * @param amount the amount of hp
     */
    boolean buyHp(int amount) {
        return game.buyHp(amount);
    }

    @Override
    public void leftClick() {
            shop.getCurrent().dispose();
            new ShopResource(main, stage, game, trader, shop);
        }
    }
}
