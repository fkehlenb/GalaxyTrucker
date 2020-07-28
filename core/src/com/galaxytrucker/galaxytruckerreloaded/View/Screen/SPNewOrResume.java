package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.*;

/**
 * Selection screen new game or resuming of old one
 */
public class SPNewOrResume implements Screen {

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the background texture
     */
    private Texture background;

    /**
     * the stage for the buttons
     */
    private Stage stage;

    /**
     * the viewport
     */
    private Viewport viewport;


    /** Constructor  */
    public SPNewOrResume(Main main){
        this.main = main;
        background = new Texture("1080p.png");


        StartNewGameButton startNewGameButton = new StartNewGameButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        ResumeButton resumeButton = new ResumeButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*1, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        SPNewOrResumeBackButton backButton = new SPNewOrResumeBackButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this, main);

        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(viewport);
        stage.addActor(startNewGameButton);
        stage.addActor(resumeButton);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when this screen becomes the current screen for a game
     */
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();
        main.batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        main.batch.end();

        stage.draw();
    }

    /**
     * @param width the new width
     * @param height the new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }

    /**
     * resume an old game
     */
    public void resumeGame() {
        if(!main.isMultiplayer()) {
            main.setScreen(new LoginScreen(main));
        }
        else {
            main.setScreen(new LoginScreen(main));
        }
        dispose();
    }

    /**
     * start a new game
     */
    public void newGame() {
        if(!main.isMultiplayer()) {
            main.setScreen(new ChooseDifficultyScreen(main));
        }
        else {
            main.setScreen(new ChooseDifficultyScreen(main));
        }
        dispose();
    }

}
