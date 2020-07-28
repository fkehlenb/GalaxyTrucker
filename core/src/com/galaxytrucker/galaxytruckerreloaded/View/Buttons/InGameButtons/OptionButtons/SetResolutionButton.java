package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;

/**
 * button for setting the resolution
 */
public class SetResolutionButton extends ImButton {

    /**
     * for setting the resolution
     */
    private Main main;

    /**
     * the window width associated with this button
     */
    private int gameWidth;

    /**
     * the window height associated with this button
     */
    private int gameHeight;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param main main class for resolution saving
     * @param gameWidth window width associated with this button
     * @param gameHeight window height associated with this button
     */
    public SetResolutionButton(float x, float y, float width, float height, Main main, int gameWidth, int gameHeight) {
        super(new Texture("options/set_button.png"), x, y, width, height);
        this.main = main;
        this.gameHeight = gameHeight;
        if (this.gameHeight == 0){this.gameHeight = 1080;}
        this.gameWidth = gameWidth;
        if(this.gameWidth == 0){this.gameWidth = 1920;}
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * set the resolution specified in main to the parameters described here
     */
    @Override
    public void leftClick() {
        main.setResolution(gameWidth, gameHeight);
    }
}
