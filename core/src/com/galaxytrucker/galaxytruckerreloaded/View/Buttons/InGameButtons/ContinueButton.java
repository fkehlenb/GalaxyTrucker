package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.PauseMenuUI;

/**
 * continue button for ingame options
 */
public class ContinueButton extends ImButton {

    /**
     * the options ui this button is on
     */
    private PauseMenuUI ui;

    /**
     * Left-Click action of the Button.
     */
    public void leftClick() {
        ui.disposePauseMenuUI();

    }

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param ui ui this button is on
     */
    public ContinueButton(float x, float y, float width, float height, PauseMenuUI ui) {
        super(new Texture("ingame_continue.png"), x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
