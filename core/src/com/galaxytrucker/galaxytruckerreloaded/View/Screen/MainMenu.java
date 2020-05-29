package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.*;

/**
 * Main menu screen
 */
public class MainMenu implements Screen {

    /**
     * The sprite batch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * The screen texture
     */
    private Texture background;

    /**
     * new game button. leads to shipselector
     */
    private NewGameButton newGame;

    /**
     * start game button. continues old game
     */
    private StartButton startGame;

    /**
     * quit button
     */
    private QuitButton quitButton;

    /**
     * Looping music track
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

    /**
     * starts a new game.
     * called by button
     */
    public void newGame() {

    }

    /**
     * resumes the existing game.
     * called by button
     */
    public void resumeGame() {

    }

    /**
     * quits.
     * called by button
     */
    public void quit()  {

    }

    /** Constructor
     * @param main - main class */
    public MainMenu(Main main){}
}
