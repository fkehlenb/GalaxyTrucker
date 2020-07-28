package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ShipSelector;

/**
 * button for creating a game in the ship selector
 */
public class CreateGameButton extends ImButton {

    /**
     * click sound
     */
    private Sound clickSound;

    /**
     * screen this button is on
     */
    private ShipSelector screen;

    /**
     * left click action
     */
    @Override
    public void leftClick() {
        screen.startGame();
    }

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen ui this button is on
     */
    public CreateGameButton(float x, float y, float width, float height, ShipSelector screen) {
        super(new Texture("buttons/start_game_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
