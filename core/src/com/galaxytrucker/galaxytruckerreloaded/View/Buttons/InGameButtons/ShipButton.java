package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

/**
 * Button for opening the Ship-Interface
 * */
public class ShipButton extends ImButton
{
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui this button is on
     */
    private ShipView ui;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param ui ui this button is on
     */
    public ShipButton(float x, float y, float width, float height, ShipView ui) {
        super(new Texture("top_ship_on.png"), x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }


    /**
     * opens the Ship-Screen of the current Ship
     */
    public void leftClick()
    {
        ui.openInventory();
    }
}
