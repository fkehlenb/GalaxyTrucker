package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class ShopUpgradeButton extends ImButton {

    private ShopUpgrade shopUpgrade;

    private ShopUI shop;
    private Main main;
    private Stage stage;
    private GamePlay game;
    private Trader trader;

    public ShopUpgradeButton(float x, float y, float width, float height, ShopWeapon shopWeapon) {
            super(new Texture("sell_buy_on.png"), x, y, width, height);

            this.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    leftClick();
                }
            });

        }

        public void leftClick()
        {
            shop.getCurrent().dispose();
            new ShopUpgrade(main, stage, game, trader, shop);
        }
    }
}
