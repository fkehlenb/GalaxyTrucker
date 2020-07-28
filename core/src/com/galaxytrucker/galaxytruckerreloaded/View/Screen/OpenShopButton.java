package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;

public class OpenShopButton extends ImButton {

    private GamePlay screen;
    private Trader trader;
    public OpenShopButton(float x, float y, float width, float height, GamePlay screen, Trader trader) {
        super(new Texture("shop/openShop.png"),  x, y, width, height);

        this.trader = trader;
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    @Override
    public void leftClick() {
        screen.createShop(trader);
    }
}

