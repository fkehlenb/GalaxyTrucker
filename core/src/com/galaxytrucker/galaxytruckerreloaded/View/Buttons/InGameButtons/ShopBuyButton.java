package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopUI;

/**
 * button used to buy something in the shop
 */
public class ShopBuyButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the item this button belongs to
     * index of list in shopui
     */
    private int item;

    /**
     * the ui this button is on
     */
    private ShopUI shop;

    /**
     * Constructor
     *
     * @param item the item
     * @param ui the ui this button is on
     */
    public ShopBuyButton(float x, float y, float width, float height, int item, ShopUI ui) {
        super(new Texture("sell_buy_on.png"), x, y, width, height);
        this.item = item;
        shop = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    public void leftClick()
    {
        shop.buy(item);
    }
}
