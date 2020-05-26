package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;

/**
 * Button for setting the degree of diffiulty
 */
public class DifficultyButton extends Button
{
    /**
     * Sprite batch
     */
    private SpriteBatch batch;
    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;
    /**
     * Background
     */
    private Texture background;
    /**
     * Click sound effect
     */
    private Sound clickSound;

    private int difficulty;

        /**
         * Constructor
         *
         * @param main - main class
         */
        public DifficultyButton(Main main) {
        }

    /**
     * Sets difficutly to a specific level
     */
    @Override
    public void leftClick()
    {
        // setDifficulty(difficulty);
    }

}

