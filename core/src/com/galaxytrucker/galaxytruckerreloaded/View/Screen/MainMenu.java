package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.NewGameButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.QuitButton;

/**
 * Main menu screen
 */
public class MainMenu implements Screen {

    private Texture background;

    private Main main;

    private Stage stage;

    private NewGameButton newGame;
    private QuitButton quit;

    /** Constructor  */
    public MainMenu(Main main){
        this.main = main;
        background = new Texture("1080p.png");
        newGame = new NewGameButton(main.WIDTH/2, main.HEIGHT/2, 248, 50, this);
        quit = new QuitButton(main.WIDTH/2, main.HEIGHT/2 - 100, 248, 50, this);

        stage = new Stage();
        stage.addActor(quit);
        stage.addActor(newGame);


        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when this screen becomes the current screen for a game
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
        Gdx.gl.glClearColor(1, 1, 1, 1);
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

    }

    /**
     * pauses
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

    /**
     * starts a new game.
     * called by button
     */
    public void newGame() {
        main.setScreen(new ShipSelector(main));
        dispose();
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
    public void quit() {
        Gdx.app.exit();
    }


}
