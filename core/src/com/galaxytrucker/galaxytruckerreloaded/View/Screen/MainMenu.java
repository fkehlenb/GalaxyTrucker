package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.galaxytrucker.galaxytruckerreloaded.Main;

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
     * SinglePlayer button
     */
    private ImageButton singlePlayerButton;

    /**
     * SinglePlayer button texture
     */
    private Texture singlePlayerButtonTexture;

    /**
     * MultiPlayer button
     */
    private ImageButton multiPlayerButton;

    /**
     * MultiPlayer button texture
     */
    private Texture multiPlayerButtonTexture;

    /**
     * Options button
     */
    private ImageButton optionsButton;

    /**
     * Option button texture
     */
    private Texture optionsButtonTexture;

    /**
     * Quit button
     */
    private ImageButton quitButton;

    /**
     * Quit button texture
     */
    private ImageButton quitButtonTexture;

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

    /** Constructor
     * @param main - main class */
    public MainMenu(Main main){}
}
