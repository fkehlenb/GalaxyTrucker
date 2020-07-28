package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopElement;

/**
 * button used to buy something in the shop
 */
public class ShopBuyButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui this button is on
     */
    private ShopElement shop;

    /**
     * Constructor
     *
     * @param ui the ui this button is on
     */
    public ShopBuyButton(Texture tex, float x, float y, float width, float height, ShopElement ui) {
        super(tex, x, y, width, height);
        shop = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    public void leftClick()
    {
        shop.buy();
    }
}
