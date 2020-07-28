package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ChooseDifficultyScreen;

/**
 * Button for setting the degree of diffiulty
 */
public class DifficultyButton extends ImButton
{
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the difficulty
     */
    private int difficulty;

    /**
     * the screen this button is on
     */
    private ChooseDifficultyScreen screen;

    /**
     * constructor
     * @param t texture for this difficulty
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen screen the button is on
     * @param difficulty difficulty this button represents
     */
    public DifficultyButton(Texture t, float x, float y, float width, float height, ChooseDifficultyScreen screen, int difficulty) {
        super(t, x, y, width, height);
        this.screen = screen;
        this.difficulty = difficulty;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * Sets difficulty to a specific level
     */
    @Override
    public void leftClick()
    {
        screen.setDifficulty(difficulty);
    }

}

