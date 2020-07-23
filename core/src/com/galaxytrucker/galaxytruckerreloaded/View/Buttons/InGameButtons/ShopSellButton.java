package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopElement;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopSell;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopSellElement;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.ShopUI;

public class ShopSellButton extends ImButton {
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui this button is on
     */
    private ShopSell shopSell;

    private ShopUI shop;
    private Main main;
    private Stage stage;
    private GamePlay game;
    private Trader trader;

    /**
     * Constructor
     *
     * @param ui the ui this button is on
     */
    public ShopSellButton(float x, float y, float width, float height, ShopUI shopUI, Trader trader, ShopSell shopSell) {
        super(new Texture("ändern"), x, y, width, height);
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    public void leftClick(){
        shop.getCurrent().dispose();
        shop.openSellUI();

    }
}
