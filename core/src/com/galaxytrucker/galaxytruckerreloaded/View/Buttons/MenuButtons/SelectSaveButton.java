package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ContinueSelectSave;

public class SelectSaveButton extends ImButton {

    private Sound clickSound;

    private ContinueSelectSave screen;

    private int save;

    @Override
    public void leftClick() {
        screen.chooseSave(save);
    }

    public SelectSaveButton(float x, float y, float width, float height, int save, ContinueSelectSave screen) {
        super(new Texture("yes.png"), x, y, width, height);
        this.screen = screen;
        this.save = save;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
