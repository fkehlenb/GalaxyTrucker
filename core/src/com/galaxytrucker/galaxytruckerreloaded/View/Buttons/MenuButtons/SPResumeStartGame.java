package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ChooseDifficultyScreen;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.SPResumeLobby;

public class SPResumeStartGame extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the screen this button is on
     */
    private SPResumeLobby screen;

    /**
     * Constructor
     *
     * @param screen the screen this button is on
     */
    public SPResumeStartGame(float x, float y, float width, float height, SPResumeLobby screen) {
        super(new Texture("continue.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * Sets difficutly to a specific level
     */
    @Override
    public void leftClick()
    {
        screen.startGame();
    }


}
