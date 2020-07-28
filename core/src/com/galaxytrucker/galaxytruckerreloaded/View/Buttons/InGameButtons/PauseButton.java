package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

/**
 * button for opening the pause menu
 */
public class PauseButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * ui this button is on
     */
    private ShipView shipView;


    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param shipView ui this button is on
     */
    public PauseButton(float x, float y, float width, float height, ShipView shipView) {
        super(new Texture("buttons/pause_button.png"), x, y, width, height);
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
        this.shipView = shipView;
    }

    /**
     * Creats new Game
     */
    public void leftClick()
    {
        shipView.createPauseMenu();
    }
}
