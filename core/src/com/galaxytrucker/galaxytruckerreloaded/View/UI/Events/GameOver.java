package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MainMenuButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

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

    /**
     * main class extending game
     */
    private Main main;

    /**
     * constructor
     * @param main main class extending game
     * @param stage stage for buttons
     * @param won whether the game was won
     */
    public GameOver(Main main, Stage stage, boolean won) {
        this.main = main;

        if(won) {
            gameOverTexture = new Texture("gameover/victory_bg.png");
        }
        else {
            gameOverTexture = new Texture("gameover/gameover_bg.png");
        }

        menuButton = new MainMenuButton(Main.WIDTH/2f-75, Main.HEIGHT/2f - Main.HEIGHT/10f, 150, 30, main);
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
        main.batch.draw(gameOverTexture, (main.WIDTH/2)-300, (main.HEIGHT/2)-215, 601, 431); //TODO whxy
        main.batch.end();
    }
}
