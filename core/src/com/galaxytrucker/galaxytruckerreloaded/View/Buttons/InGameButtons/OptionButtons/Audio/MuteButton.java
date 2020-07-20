package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.audio.*;
import com.galaxytrucker.galaxytruckerreloaded.Controller.AudioController;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;


public class MuteButton extends ImButton {

    private Sound sound;
    private Main main;

    public MuteButton(float x, float y, float width, float height, Main main){
        super(new Texture("options/Audio_mute.png"), x, y, width, height);
        this.main = main;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    @Override
    public void leftClick(){
        if(AudioController.getInstance().getMusic().isPlaying()){
            AudioController.getInstance().mute();
        } else AudioController.getInstance().play();
    }
}
