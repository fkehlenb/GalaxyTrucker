package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.SetResolutionButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video.WindowedButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video.FullscreenEnableButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

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

    private MainMenu mainMenu;

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

    private FullscreenEnableButton fullscreenEnableButton;

    private WindowedButton windowedButton;

    private SetResolutionButton setResolutionButton;

    private Stage stage;

    /**
     * Constructor
     * @param main current Main instance
     * @param stage current stage instance
     * @param gamePlay current gameplay instance
     */
    public VideoUI (Main main, Stage stage, GamePlay gamePlay, MainMenu mainMenu) {
        this.main = main;
        this.gamePlay = gamePlay;
        this.mainMenu = mainMenu;
        if(gamePlay!=null) {
            this.optionUI = gamePlay.getOptionUI();
        }
        else if(mainMenu != null) {
            this.optionUI = mainMenu.getOptionUI();
        }
        this.stage = stage;
        backgroundTexture = new Texture("options/video.png");

        x = main.WIDTH / 2 - backgroundTexture.getWidth() / 2;
        y = main.HEIGHT / 2 - backgroundTexture.getHeight() / 2;


        backButton = new BackButton(x + 220, y + 100, 128, 24, optionUI, this);
        windowedButton = new WindowedButton(x + 220+65, y + 320, 128, 24, main);
        fullscreenEnableButton = new FullscreenEnableButton(x + 220-65, y + 320, 128, 24, main);
        setResolutionButton = new SetResolutionButton(x + 220, y + 420, 128, 24, main, 2560, 1440);


        stage.addActor(backButton);
        stage.addActor(windowedButton);
        stage.addActor(fullscreenEnableButton);
        stage.addActor(setResolutionButton);
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

        stage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposeVideoUI() {
        backgroundTexture.dispose();
        backButton.remove();
        fullscreenEnableButton.remove();
        windowedButton.remove();
        setResolutionButton.remove();
        if(gamePlay != null) {
            gamePlay.deleteVideoUI();
        }
        else if(mainMenu != null) {
            mainMenu.deleteVideoUI();
        }
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
        windowedButton.setVisible(true);
        fullscreenEnableButton.setVisible(true);
    }

    /**
     * Close the options menu
     */
    public void hideVideoUI() {
        backButton.setVisible(false);
        windowedButton.setVisible(false);
        fullscreenEnableButton.setVisible(false);
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeVideoUI();
            optionUI.showOptionsUI();
        }
    }
}
