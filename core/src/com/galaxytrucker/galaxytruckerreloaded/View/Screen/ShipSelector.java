package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.DifficultyButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.ShipSelectButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SinglePlayerButton;

/**
 * Ship selector screen when creating new game
 */
public class ShipSelector implements Screen {

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
     * the textures of the ships to choose from
     */
    private List<Texture> ships;

    /**
     * the buttons for the ships
     */
    private List<ShipSelectButton> shipButtons;

    /**
     * difficulty buttons (easy, medium, hard)
     */
    private List<DifficultyButton> difficulties;

    /**
     * button to choose single player. Otherwise, it is multiplayer
     */
    private SinglePlayerButton singlePlayerButton;

    /**
     * Looping music
     */
    private Music music;

    /**
     * Click sound effect
     */
    private Sound clickSound;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /** Constructor
     * @param main - main class */
    public ShipSelector(Main main){}
}
