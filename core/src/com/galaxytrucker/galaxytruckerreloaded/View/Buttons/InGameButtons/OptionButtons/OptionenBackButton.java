package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.PauseMenuUI;

/**
 * button for going back when option ui on pause menu
 */
public class OptionenBackButton extends ImButton {

    /**
     * click sound
     */
    private Sound clickSound;

    /**
     * ui to be disposed
     */
    private OptionUI optionUI;

    /**
     * ui to go back to
     */
    private PauseMenuUI pauseMenuUI;

    /**
     * constructor
     * @param x x postion
     * @param y y position
     * @param width button width
     * @param height button height
     * @param optionUI ui to go back to
     * @param pauseMenuUI current ui to be disposed
     */
    public OptionenBackButton(float x, float y, float width, float height, OptionUI optionUI, PauseMenuUI pauseMenuUI) {
        super(new Texture("options/escape_back_on.png"), x, y, width, height);
        this.optionUI = optionUI;
        this.pauseMenuUI = pauseMenuUI;
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
        optionUI.disposeOptionsUI();
        if(pauseMenuUI!=null) {
            pauseMenuUI.showPauseMenuUI();
        }
    }
}
