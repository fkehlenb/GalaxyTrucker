package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Controller.HangerController;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.DifficultyButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.ShipSelectButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SinglePlayerButton;

import java.util.LinkedList;
import java.util.List;

/**
 * Ship selector screen when creating new game
 */
public class ShipSelector implements Screen {

    private Main main;

    private Stage stage;

    /**
     * Background
     */
    private Texture background;

    /**
     * the textures of the ships to choose from
     */
    private List<Texture> ships;

    /**
     * the buttons for the ships
     */
    private List<ShipSelectButton> shipButtons;

    /**
     * difficulty buttons (easy, medium, hard)
     */
    private List<DifficultyButton> difficulties;

    /**
     * button to choose single player. Otherwise, it is multiplayer
     */
    private SinglePlayerButton singlePlayerButton;

    private HangerController controller;

    /** Constructor TODO wie werden die schiffe dargestellt
     * @param main - main class */
    public ShipSelector(Main main){
        this.main = main;
        controller = new HangerController();
        //TODO methode um mögliche schiffe zu bekommen, dann für jeden eine Textur in liste + einen button

        background = new Texture("1080p.png");

        stage = new Stage();

        singlePlayerButton = new SinglePlayerButton(main.WIDTH-100, main.HEIGHT-100, 248, 50, this);
        stage.addActor(singlePlayerButton);
        difficulties = new LinkedList<>();
        for(int i=0; i<=3; i++) {
            DifficultyButton difficulty1 = new DifficultyButton(main.WIDTH-100, main.HEIGHT/2 - 100 - 75*i, 248, 50, this, i);
            difficulties.add(difficulty1);
            stage.addActor(difficulty1);
        }

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
     * to set the difficulty. called by button
     * @param difficulty the difficulty
     */
    public void setDifficulty(int difficulty) {

    }

    /**
     * the ship is selected
     * @param ship the index of the ship in the list of possible ships
     */
    public void setShip(int ship) {
        //TODO was für 1 username??
    }

    /**
     * sets whether or not singleplayer. called by button
     * @param single singleplayer = true
     */
    public void setSinglePlayer(boolean single) {

    }
}
