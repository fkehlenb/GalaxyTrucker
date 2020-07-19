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
    private float x, y;
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

        x = main.WIDTH / 2 - audioBackgroundTexture.getWidth() / 2;
        y = main.HEIGHT / 2 - audioBackgroundTexture.getHeight() / 2;

        backButton = new BackButton(x + 220, y + 100, 128, 24, optionUI, this);
        muteButton = new MuteButton(x + 220, y + (3*audioBackgroundTexture.getHeight() / 5),128,24,main);
        volumeDownButton = new VolumeDownButton(x + 220 - 130, y + (3*audioBackgroundTexture.getHeight() / 5) ,128,24, main);
        volumeUpButton = new VolumeUpButton(x + 220 + 130, y + (3*audioBackgroundTexture.getHeight() / 5) ,128,24,main);

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
        main.batch.draw(audioBackgroundTexture, x, y, 601, 471);
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
     * TODO: funktioniert nicht, ist auch nicht zwingend Nötig
     */
    public void updateInput() {
        //macht nicht was es soll. im Kern ein Bastard diese Methode
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            backButton.leftClick();
        }
    }

}