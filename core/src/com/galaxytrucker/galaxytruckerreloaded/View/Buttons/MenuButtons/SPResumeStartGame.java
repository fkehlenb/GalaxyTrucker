package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.SPResumeLobby;

/**
 * button for continuing an old single player game
 */
public class SPResumeStartGame extends ImButton {

    /**
     * the screen this button is on
     */
    private SPResumeLobby screen;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen screen the button is on
     */
    public SPResumeStartGame(float x, float y, float width, float height, SPResumeLobby screen) {
        super(new Texture("buttons/start_game_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * continue the game
     */
    @Override
    public void leftClick()
    {
        screen.startGame();
    }


}
