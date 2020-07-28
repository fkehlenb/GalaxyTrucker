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

public class AudioUI {

    private Texture audioBackgroundTexture;
    private Main main;
    private GamePlay gamePlay;
    private OptionUI optionUI;
    private BackButton backButton;
    private Stage stage;
    private MainMenu mainMenu;
    private MuteButton muteButton;
    private VolumeUpButton volumeUpButton;
    private VolumeDownButton volumeDownButton;

    /**
     * Constructor
     * @param main
     * @param stage
     * @param gamePlay
     */
    public AudioUI(Main main, Stage stage, GamePlay gamePlay, MainMenu mainMenu) {
        this.main = main;
        this.gamePlay = gamePlay;
        this.mainMenu = mainMenu;
        //this.optionUI = gamePlay.getOptionUI();
        if(gamePlay!=null) {
            this.optionUI = gamePlay.getOptionUI();
        }
        else if(mainMenu != null) {
            this.optionUI = mainMenu.getOptionUI();
        }
        this.stage = stage;
        audioBackgroundTexture = new Texture("options/audio.png");

        backButton = new BackButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*4, main.WIDTH/15, main.HEIGHT/40, optionUI, this);

           

        muteButton = new MuteButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*5, main.WIDTH/15, main.HEIGHT/40);
        volumeDownButton = new VolumeDownButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*6, main.WIDTH/15, main.HEIGHT/40);
        volumeUpButton = new VolumeUpButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*7, main.WIDTH/15, main.HEIGHT/40);

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
        main.batch.draw(audioBackgroundTexture, main.WIDTH/2 - (main.WIDTH/3.1946f)/2, main.HEIGHT/2 - (main.HEIGHT/2.293f)/2, main.WIDTH/3.1946f, main.HEIGHT/2.293f);
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
     * Open the options menu
     */
    public void showAudioUI() {
        backButton.setVisible(true);
        volumeDownButton.setVisible(true);
        volumeUpButton.setVisible(true);
        muteButton.setVisible(true);

    }
    /**
     * Close the options menu
     */
    public void hideAudioUI() {
        backButton.setVisible(false);
        volumeDownButton.setVisible(false);
        volumeUpButton.setVisible(false);
        muteButton.setVisible(false);
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
         if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeAudioUI();
            optionUI.showOptionsUI();
        }
    }

}