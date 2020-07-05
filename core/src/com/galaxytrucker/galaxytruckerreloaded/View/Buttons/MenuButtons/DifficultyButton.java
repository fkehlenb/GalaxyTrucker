package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ChooseDifficultyScreen;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ShipSelector;
import org.w3c.dom.Text;

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
     * Constructor
     *
     * @param screen the screen this button is on
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
     * Sets difficutly to a specific level
     */
    @Override
    public void leftClick()
    {
        screen.setDifficulty(difficulty);
    }

}

