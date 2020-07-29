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

/**
 * video options ui
 */
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
     * screen this is on, if during main menu
     */
    private MainMenu mainMenu;

    /**
     * OptionUI of the running Optionmenu.
     */
    private OptionUI optionUI;

    /**
     * Back Button
     */
    private BackButton backButton;

    /**
     * button for enabling full screen
     */
    private FullscreenEnableButton fullscreenEnableButton;

    /**
     * button for disabling full screen
     */
    private WindowedButton windowedButton;

    /**
     * button for changing the resolution
     */
    private SetResolutionButton setResolutionButton;

    /**
     * stage for buttons
     */
    private Stage stage;

    /**
     * Constructor
     * @param main current Main instance
     * @param stage current stage instance
     * @param gamePlay current gameplay instance
     * @param mainMenu screen this is on, if during main menu
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


        backButton = new BackButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*4, Main.WIDTH/15f, Main.HEIGHT/40f, optionUI, this);
        windowedButton = new WindowedButton(Main.WIDTH/2f , Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*2, Main.WIDTH/15f, Main.HEIGHT/40f);
        fullscreenEnableButton = new FullscreenEnableButton(Main.WIDTH/2f - (Main.WIDTH/15f), Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*2, Main.WIDTH/15f, Main.HEIGHT/40f);
        setResolutionButton = new SetResolutionButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*1, Main.WIDTH/15f, Main.HEIGHT/40f, main, 2560, 1440);


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
        main.batch.draw(backgroundTexture, Main.WIDTH/2f - (Main.WIDTH/3.1946f)/2, Main.HEIGHT/2f - (Main.HEIGHT/2.293f)/2, Main.WIDTH/3.1946f, Main.HEIGHT/2.293f);
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
     * handles input to pause game, open options
     * TODO: funktioniert nicht, ist auch nicht zwingend Nötig
     */
    private void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
           backButton.leftClick();
        }
    }
}
