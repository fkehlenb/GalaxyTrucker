package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShopButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopSellElement;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.Shop.ShopUI;

public class ShopSellButton extends ImButton {
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui this button is on
     */
    private ShopSellElement shopSellElement;

    private ShopUI shop;
    private Main main;
    private Stage stage;
    private GamePlay game;
    private Trader trader;

    /**
     * Constructor
     *
     * @param shopSellElement the ui this button is on
     */
    public ShopSellButton(Texture texture, float x, float y, float width, float height, ShopUI shopUI,  ShopSellElement shopSellElement) {
        super(texture, x, y, width, height);
        this.shopSellElement = shopSellElement;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    public void leftClick(){
        shopSellElement.sell();

    }
}
