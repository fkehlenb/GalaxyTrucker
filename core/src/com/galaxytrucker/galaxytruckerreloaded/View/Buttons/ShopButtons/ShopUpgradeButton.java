package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopUpgrade;

public class ShopUpgradeButton extends ImButton {

    private ShopUpgrade shopUpgrade;

    private ShopUI shop;
    private Main main;
    private Stage stage;
    private GamePlay game;
    private Trader trader;

    public ShopUpgradeButton(float x, float y, float width, float height, ShopUI shopUI) {
        super(new Texture("sell_buy_on.png"), x, y, width, height);

        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });

    }

    @Override
    public void leftClick()
    {
        shop.getCurrent().dispose();
        new ShopUpgrade(main, stage, game, trader, shop);
    }
}

