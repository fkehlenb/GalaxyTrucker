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

    /**
     *  button for opening video options
     */
    private VideoButton videoButton;

    /**
     * button for opening general options
     */
    private GeneralButton generalButton;

    /**
     * button for opening control options
     */
    private ControlButton controlButton;

    /**
     * button for opening credits
     */
    private CreditsButton creditsButton;

    /**
     * button for opening audio options
     */
    private AudioButton audioButton;

    /**
     * main class extending game
     */
    private Main main;

    /**
     * screen this is on, if during game
     */
    private GamePlay game;

    /**
     * screen this is on, if during main menu
     */
    private MainMenu mainMenu;

    /**
     * pause menu ui, if during game
     */
    private PauseMenuUI pauseMenuUI;

    /**
     * stage for buttons
     */
    private Stage stage;

    /**
     * back button
     */
    private OptionenBackButton optionenBackButton;

    /**
     * Constructor
     *
     * @param main - main class
     * @param mainMenu screen this is on, if during main menu
     * @param game screen this is on, if during game
     * @param stage stage for buttons
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

        optionenBackButton =    new OptionenBackButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*4, Main.WIDTH/15f, Main.HEIGHT/40f, this, pauseMenuUI);
        videoButton =           new VideoButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*3, Main.WIDTH/15f, Main.HEIGHT/40f, this);
        generalButton =         new GeneralButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*2, Main.WIDTH/15f, Main.HEIGHT/40f, this);
        controlButton =         new ControlButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*1, Main.WIDTH/15f, Main.HEIGHT/40f, this );
        creditsButton =         new CreditsButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*0, Main.WIDTH/15f, Main.HEIGHT/40f, this);
        audioButton =           new AudioButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 +Main.HEIGHT/40f*1, Main.WIDTH/15f, Main.HEIGHT/40f, this);

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
        main.batch.draw(optionsBackgroundTexture, Main.WIDTH/2f - (Main.WIDTH/3.1946f)/2, Main.HEIGHT/2f - (Main.HEIGHT/2.293f)/2, Main.WIDTH/3.1946f, Main.HEIGHT/2.293f);
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
    private void hideOptionsUI() {
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
    private void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            optionenBackButton.leftClick();
        }
    }

    /**
     * open general options
     */
    public void openGeneral() {
        this.hideOptionsUI();
        if(game!=null) {
            game.createGeneralUI();
        }
        else if(mainMenu!=null) {
            mainMenu.createGeneralUI();
        }
    }

    /**
     * open video options
     */
    public void openVideo() {
        this.hideOptionsUI();
        if(game!=null) {
            game.createVideoUI();
        }
        else if(mainMenu!=null) {
            mainMenu.createVideoUI();
        }
    }

    /**
     * open credits
     */
    public void openCredits(){
        this.hideOptionsUI();
        if(game !=null){
            game.createCreditUI();
        }
        else if(mainMenu != null){
            mainMenu.createCreditUI();
        }
    }

    /**
     * open audio options
     */
    public void openAudio(){
        this.hideOptionsUI();
        if(game != null){
            game.createAudioUI();
        }
        else if(mainMenu != null){
            mainMenu.createAudioUI();
        }
    }

    /**
     * open control options
     */
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
