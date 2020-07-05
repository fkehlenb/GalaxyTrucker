package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ShipSelector;

/**
 * the button representing one ship in the ship selector
 */
public class ShipSelectButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ship this button represents in the ship selector
     */
    private int ship;

    /**
     * the screen this button is on
     */
    private ShipSelector screen;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.setShip(ship);
    }

    /**
     * constructor
     * @param ship the ship, (index of ship in list in shipselector)
     * @param screen the screen this button is on
     */
    public ShipSelectButton(float x, float y, float width, float height, int ship, ShipSelector screen) {
        super(new Texture("yes.png"), x, y, width, height);
        this.screen = screen;
        this.ship = ship;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
