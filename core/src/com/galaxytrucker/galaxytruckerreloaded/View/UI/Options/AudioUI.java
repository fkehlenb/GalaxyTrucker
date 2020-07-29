package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio.MuteButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio.VolumeDownButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio.VolumeUpButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

/**
 * ui for the audio options
 */
public class AudioUI {

    /**
     * background texture
     */
    private Texture audioBackgroundTexture;

    /**
     * main class extending game
     */
    private Main main;

    /**
     * screen this ui is on, if on gameplay
     */
    private GamePlay gamePlay;

    /**
     * screen this ui is on, if on the menu screen
     */
    private MainMenu mainMenu;

    /**
     * previous ui element
     */
    private OptionUI optionUI;

    /**
     * button for returning to last ui element (options)
     */
    private BackButton backButton;

    /**
     * stage for buttons
     */
    private Stage stage;

    /**
     * button for muting audio
     */
    private MuteButton muteButton;

    /**
     * button for raising the volume
     */
    private VolumeUpButton volumeUpButton;

    /**
     * button for lowering volume
     */
    private VolumeDownButton volumeDownButton;

    /**
     * constructor
     * @param main main class extending game
     * @param stage stage for buttons
     * @param gamePlay screen, if during game
     * @param mainMenu screen, if on main menu
     */
    public AudioUI(Main main, Stage stage, GamePlay gamePlay, MainMenu mainMenu) {
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
        audioBackgroundTexture = new Texture("options/audio.png");

        backButton = new BackButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*4, Main.WIDTH/15f, Main.HEIGHT/40f, optionUI, this);

           

        muteButton = new MuteButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*5, Main.WIDTH/15f, Main.HEIGHT/40f);
        volumeDownButton = new VolumeDownButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*6, Main.WIDTH/15f, Main.HEIGHT/40f);
        volumeUpButton = new VolumeUpButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*7, Main.WIDTH/15f, Main.HEIGHT/40f);

        stage.addActor(backButton);
        stage.addActor(muteButton);
        stage.addActor(volumeDownButton);
        stage.addActor(volumeUpButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        updateInput();
        main.batch.begin();
        main.batch.draw(audioBackgroundTexture, Main.WIDTH/2f - (Main.WIDTH/3.1946f)/2, Main.HEIGHT/2f - (Main.HEIGHT/2.293f)/2, Main.WIDTH/3.1946f, Main.HEIGHT/2.293f);
        main.batch.end();

        stage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposeAudioUI() {
        audioBackgroundTexture.dispose();
        backButton.remove();
        volumeUpButton.remove();
        volumeDownButton.remove();
        muteButton.remove();
        if(gamePlay != null){
            gamePlay.deleteAudioUI();
        }
        else if(mainMenu != null){
            mainMenu.deleteAudioUI();
        }
    }

    /**
     * handles input to pause game, open options
     */
    private void updateInput() {
         if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeAudioUI();
            optionUI.showOptionsUI();
        }
    }

}