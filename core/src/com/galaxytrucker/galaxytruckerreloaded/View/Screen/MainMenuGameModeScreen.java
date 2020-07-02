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
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

/**
 * Selection screen for Singleplayer or Multiplayer
 */
public class MainMenuGameModeScreen implements Screen {

    private Main main;

    private Texture background;

    private Stage stage;

    private SinglePlayerButton singlePlayerButton;

    private MultiPlayerButton multiPlayerButton;

    private MainMenuGameModeScreenBackButton mainMenuGameModeScreenBackButton;

    private Viewport viewport;


    /** Constructor  */
    public MainMenuGameModeScreen(Main main){
        this.main = main;
        background = new Texture("1080p.png");

        singlePlayerButton = new SinglePlayerButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24, 512, 48, this);
        multiPlayerButton = new MultiPlayerButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24 -96, 512, 48, this);
        mainMenuGameModeScreenBackButton = new MainMenuGameModeScreenBackButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24 -96*2, 512, 48, this, main);

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);
        stage.addActor(singlePlayerButton);
        stage.addActor(multiPlayerButton);
        stage.addActor(mainMenuGameModeScreenBackButton);

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

    public void setSingleplayer() {
        main.setScreen(new SPNewOrResume(main));
        dispose();
    }

    public void setMultiplayer() {

    }
}
