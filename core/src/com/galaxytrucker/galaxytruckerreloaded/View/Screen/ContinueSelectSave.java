package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.ContinueSelectSaveBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SelectSaveButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.GameOver;

import java.util.List;

public class ContinueSelectSave implements Screen {

    private Main main;

    private Stage stage;

    private Viewport viewport;

    private Texture background;

    public ContinueSelectSave(Main main) {
        this.main = main;

        background = new Texture("1080p.png");

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        //spielstände vom server holen, für jeden einen SelectSaveButton.
        //buttons zu stage hinzufügen
        SelectSaveButton button1 = new SelectSaveButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24, 512, 48, 0, this);
        SelectSaveButton button2 = new SelectSaveButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24 - 96, 512, 48, 1, this);

        ContinueSelectSaveBackButton backButton = new ContinueSelectSaveBackButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24 - 96*2, 512, 48, this, main);

        stage.addActor(button1);
        stage.addActor(button2);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    public void chooseSave(int save) {
        //call to controller
        main.setScreen(new GamePlay(main));
        dispose();
    }

    /**
     * Called when this screen becomes the current screen for a game.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        main.batch.end();

        stage.draw();
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     */
    @Override
    public void pause() {

    }

    /**
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a game
     */
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
}
