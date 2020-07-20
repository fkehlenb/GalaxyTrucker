package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Controller.VideoController;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;

public class WindowedButton extends ImButton {

    private Sound clickSound;

    private Main main;


    public WindowedButton(float x, float y, float width, float height, Main main) {
        super(new Texture("options/escape_window_on.png"), x, y, width, height);
        this.main = main;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    @Override
    public void leftClick() {
        VideoController.getInstance().setWindowed();
    }
}
