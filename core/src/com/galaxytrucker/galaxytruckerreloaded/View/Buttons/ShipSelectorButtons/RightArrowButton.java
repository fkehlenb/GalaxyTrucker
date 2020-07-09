package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShipSelectorButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.SPNewOrResume;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ShipSelector;

public class RightArrowButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the screen this button is on
     */
    private ShipSelector screen;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.nextShip();
    }

    /**
     * constructor
     *
     * @param screen  the screen this button is on
     */
    public RightArrowButton(float x, float y, float width, float height, ShipSelector screen) {
        super(new Texture("buttons/pfeil_rechts_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }


}
