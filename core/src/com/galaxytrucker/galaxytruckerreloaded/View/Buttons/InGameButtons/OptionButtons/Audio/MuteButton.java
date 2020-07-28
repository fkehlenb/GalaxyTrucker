package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Controller.AudioController;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;

/**
 * button for muting the audio
 */
public class MuteButton extends ImButton {

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     */
    public MuteButton(float x, float y, float width, float height){
        super(new Texture("options/Audio_mute.png"), x, y, width, height);
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
    public void leftClick(){
        if(AudioController.getInstance().getMusic().isPlaying()){
            AudioController.getInstance().mute();
        } else AudioController.getInstance().play();
    }
}
