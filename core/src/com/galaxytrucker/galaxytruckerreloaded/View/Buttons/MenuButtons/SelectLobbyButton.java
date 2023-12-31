package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.SelectLobbyScreen;

/**
 * button for continuing with the stats entered on the select lobby screen
 */
public class SelectLobbyButton extends ImButton {

    /**
     * the screen this button is on
     */
    private SelectLobbyScreen screen;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.joinLobby();
    }

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen screen the button is on
     */
    public SelectLobbyButton(float x, float y, float width, float height, SelectLobbyScreen screen) {
        super(new Texture("buttons/start_game_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
