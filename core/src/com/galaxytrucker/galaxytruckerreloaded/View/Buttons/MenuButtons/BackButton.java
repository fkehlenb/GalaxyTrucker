package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.*;

/**
 * button to go back to last screen in the game creation process
 */
public class BackButton extends ImButton {

    private MenuScreen screen;

    /**
     * constructor create or join
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen screen the button is on
     */
    public BackButton(float x, float y, float width, float height, MenuScreen screen) {
        super(new Texture("buttons/back_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.goBack();
    }
}
