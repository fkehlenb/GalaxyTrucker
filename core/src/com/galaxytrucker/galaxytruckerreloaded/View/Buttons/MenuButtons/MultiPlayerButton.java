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
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenuGameModeScreen;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ShipSelector;

/**
 * button to choose single player in the ship selector
 */
public class MultiPlayerButton extends ImButton {
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the screen this button is on
     */
    private MainMenuGameModeScreen screen;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.setMultiplayer();
    }

    /**
     * constructor
     *
     * @param screen  the screen this button is on
     */
    public MultiPlayerButton(float x, float y, float width, float height, MainMenuGameModeScreen screen) {
        super(new Texture("buttons/multiplayer_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
