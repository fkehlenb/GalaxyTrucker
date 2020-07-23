package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopSystem;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopUI;

public class ShopSystemButton extends ImButton {

    private ShopSystem shopSystem;

    private ShopUI shop;
    private Main main;
    private Stage stage;
    private GamePlay game;
    private Trader trader;

    public ShopSystemButton(int x, int y, int wx, int wy, ShopUI shopUI, Object o, Object o1) {
        //TODO texturen übergeben

        super(new Texture("Shop/SystemTab"),x,y,wx,wy);
    }

    @Override
    public void leftClick() {
        shop.getCurrent().dispose();
        new ShopSystem(main, stage, game, trader, shop);
    }

}
