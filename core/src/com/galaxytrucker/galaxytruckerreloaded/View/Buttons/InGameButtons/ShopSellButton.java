package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopElement;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopSellElement;

public class ShopSellButton extends ImButton {
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui this button is on
     */
    private ShopSellElement shop;

    /**
     * Constructor
     *
     * @param ui the ui this button is on
     */
    public ShopSellButton(float x, float y, float width, float height, ShopSellElement ui) {
        super(new Texture("sell_buy_on.png"), x, y, width, height);
        shop = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    public void leftClick()
    {
        shop.sell();
    }
}
