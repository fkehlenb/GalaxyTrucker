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
 * Selection screen for Singleplayer or Multiplayer
 */
public class SPNewOrResume implements Screen {

    private Main main;

    private Texture background;

    private Stage stage;

    private ResumeButton resumeButton;

    private StartNewGameButton startNewGameButton;

    private SPNewOrResumeBackButton backButton;

    private Viewport viewport;


    /** Constructor  */
    public SPNewOrResume(Main main){
        this.main = main;
        background = new Texture("1080p.png");


        startNewGameButton = new StartNewGameButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24, 512, 48, this);
        resumeButton = new ResumeButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24 - 96, 512, 48, this);
        backButton = new SPNewOrResumeBackButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24 - 96*2, 512, 48, this, main);

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
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
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
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

    public void resumeGame() {

    }

    public void newGame() {
        main.setScreen(new ShipSelector(main));
    }

}
