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
 * button to choose single player in the ship selector
 */
public class SinglePlayerButton extends ImButton {
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the screen this button is on
     */
    private ShipSelector screen;

    /**
     * whether or not it is single player
     */
    private boolean singlePlayer;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        singlePlayer = !singlePlayer;
        screen.setSinglePlayer(singlePlayer);
    }

    /**
     * constructor
     *
     * @param screen  the screen this button is on
     */
    public SinglePlayerButton(float x, float y, float width, float height, ShipSelector screen) {
        super(new Texture("yes.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
