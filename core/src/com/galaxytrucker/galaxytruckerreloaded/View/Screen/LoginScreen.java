package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.LoginButton;

/**
 * Login Screen
 */
public class LoginScreen implements Screen {

    /**
     * Background image
     */
    private Texture background;

    /**
     * Username input text field
     */
    private TextField username;

    /**
     * the login button
     */
    private LoginButton loginButton;

    /**
     * Looping music track
     */
    private Music music;

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the main game class
     */
    private Main main;

    /**
     * the stage
     */
    private Stage stage;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public LoginScreen(Main main) {
        this.main = main;
        background = new Texture("1080p.png");
        loginButton = new LoginButton(main.WIDTH/2, main.HEIGHT/2, 248, 50, this);
        username = new TextField("", new Skin());

        stage = new Stage();
        stage.addActor(loginButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        main.batch.end();
        stage.draw();
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
        background.dispose();
        stage.dispose();
    }

    /**
     * login method, called by the button
     */
    public void login() {
        String name = username.getText();
        //TODO welcher controller??
    }
}
