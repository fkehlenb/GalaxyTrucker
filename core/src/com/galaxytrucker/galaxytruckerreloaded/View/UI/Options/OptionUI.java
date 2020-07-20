package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio.AudioButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Control.ControlButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Credits.CreditsButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.General.GeneralButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.OptionenBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video.VideoButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

/**
 * Ingame Option screen
 */
public class OptionUI {

    /**
     * Options ui background
     */
    private Texture optionsBackgroundTexture;

    private VideoButton videoButton;

    private GeneralButton generalButton;

    private ControlButton controlButton;

    private CreditsButton creditsButton;

    private AudioButton audioButton;

    private Main main;

    private GamePlay game;

    private PauseMenuUI pauseMenuUI;

    private Stage stage;

    private MainMenu mainMenu;

    private float scale;

    private OptionenBackButton optionenBackButton;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public OptionUI(Main main, Stage stage, GamePlay game, MainMenu mainMenu) {
        this.main = main;
        this.game = game;
        this.stage = stage;
        this.mainMenu = mainMenu;
        if(game != null){
            this.pauseMenuUI = game.getPauseMenuUI();
        }
        optionsBackgroundTexture = new Texture("options/options.png");

        scale = main.WIDTH/1920;

        //main.WIDTH/2 - main.WIDTH/7.74f/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 -main.HEIGHT/21.6f*1, main.WIDTH/7.74f, main.HEIGHT/21.6f

        optionenBackButton =    new OptionenBackButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*4, main.WIDTH/15, main.HEIGHT/40, this, pauseMenuUI);
        videoButton =           new VideoButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*3, main.WIDTH/15, main.HEIGHT/40, this);
        generalButton =         new GeneralButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*2, main.WIDTH/15, main.HEIGHT/40, this);
        controlButton =         new ControlButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*1, main.WIDTH/15, main.HEIGHT/40, this );
        creditsButton =         new CreditsButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*0, main.WIDTH/15, main.HEIGHT/40, this);
        audioButton =           new AudioButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 +main.HEIGHT/40*1, main.WIDTH/15, main.HEIGHT/40, this);

        stage.addActor(optionenBackButton);
        stage.addActor(generalButton);
        stage.addActor(videoButton);
        stage.addActor(controlButton);
        stage.addActor(creditsButton);
        stage.addActor(audioButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        updateInput();
        main.batch.begin();
        main.batch.draw(optionsBackgroundTexture, main.WIDTH/2 - (main.WIDTH/3.1946f)/2, main.HEIGHT/2 - (main.HEIGHT/2.293f)/2, main.WIDTH/3.1946f, main.HEIGHT/2.293f);
        main.batch.end();
        stage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposeOptionsUI() {
        optionsBackgroundTexture.dispose();
        optionenBackButton.remove();
        generalButton.remove();
        videoButton.remove();
        audioButton.remove();
        controlButton.remove();
        creditsButton.remove();
        if(mainMenu == null){
            game.deleteOptions();
        }
        else{
            mainMenu.deleteOptions();
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
    public void showOptionsUI() {
        generalButton.setVisible(true);
        optionenBackButton.setVisible(true);
        videoButton.setVisible(true);
        audioButton.setVisible(true);
        creditsButton.setVisible(true);
        controlButton.setVisible(true);

    }

    /**
     * Close the options menu
     */
    public void hideOptionsUI() {
        generalButton.setVisible(false);
        optionenBackButton.setVisible(false);
        videoButton.setVisible(false);
        audioButton.setVisible(false);
        creditsButton.setVisible(false);
        controlButton.setVisible(false);
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            optionenBackButton.leftClick();
        }
    }

    public void openGeneral() {
        this.hideOptionsUI();
        if(game!=null) {
            game.createGeneralUI();
        }
        else if(mainMenu!=null) {
            mainMenu.createGeneralUI();
        }
    }

    public void openVideo() {
        this.hideOptionsUI();
        if(game!=null) {
            game.createVideoUI();
        }
        else if(mainMenu!=null) {
            mainMenu.createVideoUI();
        }
    }

    public void openCredits(){
        this.hideOptionsUI();
        if(game !=null){
            game.createCreditUI();
        }
        else if(mainMenu != null){
            mainMenu.createCreditUI();
        }
    }

    public void openAudio(){
        this.hideOptionsUI();
        if(game != null){
            game.createAudioUI();
        }
        else if(mainMenu != null){
            mainMenu.createAudioUI();
        }
    }

    public void openControl(){
        this.hideOptionsUI();
        if(game != null){
            game.createControlUI();
        }
        else if(mainMenu != null){
            mainMenu.createControlUI();
        }
    }

}
