package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class NextRoundButton extends ImButton {

    /**
     * the sound that the button makes when it is clicked
     */
    private Sound clickSound;

    /**
     * the screen this button is on
     */
    private GamePlay screen;

    public NextRoundButton(float x, float y, float width, float height, GamePlay screen) {
        super(new Texture("ingame_continue.png"), x, y, width, height);
        this.screen = screen;
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
        screen.nextFightRound();
    }
}