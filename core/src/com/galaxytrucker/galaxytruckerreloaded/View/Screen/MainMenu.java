package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.NewGameButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.OptionButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.QuitButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionsUI;

/**
 * Main menu screen
 */
public class MainMenu implements Screen {

    private Texture background;

    private Main main;

    private Stage stage;

    private Viewport viewport;

    private OptionsUI ui;

    private NewGameButton newGame;
    private OptionButton optionButton;
    private QuitButton quit;

    /** Constructor  */
    public MainMenu(Main main){
        ui =  new OptionsUI(main, stage, game);
        this.main = main;
        background = new Texture("1080p.png");
        newGame = new NewGameButton(main.WIDTH/2 - 124, main.HEIGHT/2 - 25, 248, 50, this);
        optionButton = new OptionButton(main.WIDTH/2 - 97, main.HEIGHT/2 -50 - 25, 194, 50, ui);
        quit = new QuitButton(main.WIDTH/2 - 62 , main.HEIGHT/2 - 100 - 25, 248, 50, this);

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);
        stage.addActor(quit);
        stage.addActor(optionButton);
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
        viewport.update(width, height);
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
