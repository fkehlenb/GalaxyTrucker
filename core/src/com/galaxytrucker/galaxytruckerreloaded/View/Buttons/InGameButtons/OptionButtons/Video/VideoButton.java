package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionUI;

/**
 * button for opening the video options
 */
public class VideoButton extends ImButton {

    /**
     * click sound
     */
    private Sound clickSound;

    /**
     * ui this is in
     */
    private OptionUI optionUI;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param optionUI ui this is on
     */
    public VideoButton(float x, float y, float width, float height, OptionUI optionUI) {
        super(new Texture("options/escape_video_on.png"), x, y, width, height);
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
        optionUI.openVideo();
    }
}
