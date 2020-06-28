package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class VideoUI {

    /**
     * background
     */
    private Texture backgroundTexture;

    /**
     * Main instance of the running game.
     */
    private Main main;

    /**
     * Gameplay instance of the running game;
     */
    private GamePlay gamePlay;

    /**
     * OptionUI of the running Optionmenu.
     */
    private OptionUI optionUI;

    /**
     * Position of the Window
     */
    private float x, y;

    /**
     * Back Button
     */
    private BackButton backButton;

    /**
     * Constructor
     * @param main current Main instance
     * @param stage current stage instance
     * @param gamePlay current gameplay instance
     */
    public VideoUI (Main main, Stage stage, GamePlay gamePlay) {
        this.main = main;
        this.gamePlay = gamePlay;
        this.optionUI = gamePlay.getOptionUI();
        backgroundTexture = new Texture("options/video.png");

        x = main.WIDTH / 2 - backgroundTexture.getWidth() / 2;
        y = main.HEIGHT / 2 - backgroundTexture.getHeight() / 2;

        backButton = new BackButton(x + 220, y + 270, 128, 24, optionUI, this);

        stage.addActor(backButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        updateInput();
        main.batch.begin();
        main.batch.draw(backgroundTexture, x, y, 601, 471);
        main.batch.end();
    }

    /**
     * Dispose of options ui
     */
    public void disposeVideoUI() {
        backgroundTexture.dispose();
        backButton.remove();
        gamePlay.deleteVideoUI();
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Open the options menu
     */
    public void showVideoUI() {
        backButton.setVisible(true);
    }

    /**
     * Close the options menu
     */
    public void hideVideoUI() {
        backButton.setVisible(false);
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            disposeVideoUI();
            optionUI.showOptionsUI();
        }
    }
}
