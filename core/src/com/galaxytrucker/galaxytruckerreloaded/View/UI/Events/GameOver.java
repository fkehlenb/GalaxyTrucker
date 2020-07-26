package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MainMenuButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

/**
 * view for the event that the player has lost or won the game
 */
public class GameOver {

    /**
     * Game over texture
     */
    private Texture gameOverTexture;

    /**
     * button to main menu
     */
    private MainMenuButton menuButton;

    private Main main;

    private Stage stage;

    private GamePlay game;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public GameOver(Main main, Stage stage, boolean won, GamePlay game) {
        this.main = main;
        this.stage = stage;
        this.game = game;

        if(won) {
            gameOverTexture = new Texture("gameover/victory_background.png");
        }
        else {
            gameOverTexture = new Texture("gameover/gameover_main.png");
        }

        menuButton = new MainMenuButton(0, 0, 10, 10, main); //TODO whxy
        stage.addActor(menuButton);

    }

    /**
     * Dispose of the ui
     */
    public void disposeGameoverUI() {
        gameOverTexture.dispose();
        menuButton.remove();
    }

    /**
     * render stuff except stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(gameOverTexture, main.WIDTH/2, main.HEIGHT/2, 100, 100); //TODO whxy
        main.batch.end();
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the ui
     */
    public void showGameOverUI() {
    }

    /**
     * hide the ui
     */
    public void hideGameOverUI() {
    }
}
