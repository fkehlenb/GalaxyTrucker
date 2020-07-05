package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Controller.HangerController;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.CreateGameButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.DifficultyButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.ShipSelectButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SinglePlayerButton;

import java.util.LinkedList;
import java.util.List;

/**
 * Ship selector screen when creating new game
 */
public class ShipSelector implements Screen {

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
     * the textures of the ships to choose from
     */
    private List<Texture> ships;

    /**
     * the buttons for the ships
     */
    private List<ShipSelectButton> shipButtons;

    /**
     * button to create game
     */
    private CreateGameButton createGameButton;

    /**
     * the viewport
     */
    private Viewport viewport;


    /** Constructor
     * @param main - main class */
    public ShipSelector(Main main){
        this.main = main;

        background = new Texture("1080p.png");

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        createGameButton = new CreateGameButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24, 512, 48, this);
        stage.addActor(createGameButton);

        //get ships from server, for each one texture and one button

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        main.batch.end();
        stage.draw();
    }

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

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }

    /**
     * the ship is selected
     * @param ship the index of the ship in the list of possible ships
     */
    public void setShip(int ship) {

    }

    /**
     * start the game
     */
    public void startGame() {
        main.setScreen(new GamePlay(main));
        dispose();
    }
}
