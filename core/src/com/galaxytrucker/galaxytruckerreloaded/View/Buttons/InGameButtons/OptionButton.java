package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.PauseMenuUI;

/**
 * creates a new OptionButton
 */
public class OptionButton extends ImButton{

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * ui this button is on
     */
    private PauseMenuUI pauseMenuUI;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param ui ui this button is on
     */
    public OptionButton(float x, float y, float width, float height, PauseMenuUI ui) {
        super(new Texture("escape_options_on.png"), x, y, width, height);
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
        this.pauseMenuUI = ui;
    }

    /**
     * Creates new Game
     */
    public void leftClick()
    {
        pauseMenuUI.openOptions();
    }

}


