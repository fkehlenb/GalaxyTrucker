package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Controller.VideoController;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;

/**
 * button for enabling fullscreen
 */
public class FullscreenEnableButton extends ImButton {

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     */
    public FullscreenEnableButton(float x, float y, float width, float height) {
        super(new Texture("options/fullsc_button.png"), x, y, width, height);
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
        VideoController.getInstance().setFullscreen();
    }
}
