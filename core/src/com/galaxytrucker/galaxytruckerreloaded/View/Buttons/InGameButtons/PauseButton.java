package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

public class PauseButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    private ShipView shipView;

    /** Menu object */
    private MainMenu mainMenu;

    /**
     * Constructor
     *
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
