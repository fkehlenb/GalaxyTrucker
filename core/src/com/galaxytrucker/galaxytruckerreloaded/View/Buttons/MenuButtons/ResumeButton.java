package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.SPNewOrResume;

/**
 * button to choose single player in the ship selector
 */
public class ResumeButton extends ImButton {

    /**
     * the screen this button is on
     */
    private SPNewOrResume screen;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.resumeGame();
    }

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen screen the button is on
     */
    public ResumeButton(float x, float y, float width, float height, SPNewOrResume screen) {
        super(new Texture("buttons/resume_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
