package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.SetResolutionButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.BackButton;
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


        backButton = new BackButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*4, main.WIDTH/15, main.HEIGHT/40, optionUI, this);
        windowedButton = new WindowedButton(main.WIDTH/2 , main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*2, main.WIDTH/15, main.HEIGHT/40);
        fullscreenEnableButton = new FullscreenEnableButton(main.WIDTH/2 - (main.WIDTH/15), main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*2, main.WIDTH/15, main.HEIGHT/40);
        setResolutionButton = new SetResolutionButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*1, main.WIDTH/15, main.HEIGHT/40, main, 2560, 1440);


        stage.addActor(backButton);
        stage.addActor(windowedButton);
        stage.addActor(fullscreenEnableButton);
        stage.addActor(setResolutionButton);
    }

    /**
     * render
     * no stage stuff
     * TODO: Absolutwerte durch Realtive Position ersetzen.
     */
    public void render() {
        updateInput();
        main.batch.begin();
        main.batch.draw(backgroundTexture, main.WIDTH/2 - (main.WIDTH/3.1946f)/2, main.HEIGHT/2 - (main.HEIGHT/2.293f)/2, main.WIDTH/3.1946f, main.HEIGHT/2.293f);
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
     * TODO: funktioniert nicht, ist auch nicht zwingend Nötig
     */
    public void updateInput() {
        //macht nicht was es soll. im Kern ein Bastard diese Methode
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
           backButton.leftClick();
        }
    }
}
