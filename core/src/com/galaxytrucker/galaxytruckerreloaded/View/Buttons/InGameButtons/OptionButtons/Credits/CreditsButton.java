package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Credits;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionUI;

/**
 * button for opening the credits
 */
public class CreditsButton extends ImButton {

    /**
     * click sound
     */
    private Sound clickSound;

    /**
     * ui this is on
     */
    private OptionUI optionUI;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param optionUI ui this button is on
     */
    public CreditsButton(float x, float y, float width, float height, OptionUI optionUI) {
        super(new Texture("options/escape_credits_on.png"), x, y, width, height);
        this.optionUI = optionUI;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * left click action
     */
    @Override
    public void leftClick() {
        optionUI.openCredits();
    }
}
