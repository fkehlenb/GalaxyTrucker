package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.*;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.*;

/**
 * Main menu screen
 */
public class MainMenu implements Screen {

    /**
     * the background texture
     */
    private Texture background;

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the stage for the buttons
     */
    private Stage stage;

    /**
     * the viewport for the stage
     */
    private Viewport viewport;

    /**
     * the option ui, if it exists
     */
    private OptionUI optionUI;

    /**
     * the general option ui, if it exists
     */
    private GeneralUI generalUI;

    /**
     * the video option ui, if it exists
     */
    private VideoUI videoUI;

    /**
     * the audio option ui, if it exists
     */
    private AudioUI audioUI;

    /**
     * the control option ui, if it exists
     */
    private ControlUI controlUI;

    /**
     * the credits ui, if it exists
     */
    private CreditsUI creditsUI;

    /**
     * the button to open the options
     */
    private OptionButton optionButton;

    /**
     * the button to choose single player with
     */
    private SinglePlayerButton singlePlayerButton;

    /**
     * the button to choose multiplayer with
     */
    private MultiPlayerButton multiPlayerButton;

    /**
     * the button to quit
     */
    private QuitButton quit;

    /** Constructor  */
    public MainMenu(Main main){
        this.main = main;
        background = new Texture("1080p.png");
        //newGame = new NewGameButton(main.WIDTH/2 - 124, main.HEIGHT/2 - 25, 248, 50, this) ;
        singlePlayerButton = new SinglePlayerButton(main.WIDTH/2 - 124, main.HEIGHT/2 + 25, 248, 50, this);
        multiPlayerButton = new MultiPlayerButton(main.WIDTH/2 - 124, main.HEIGHT/2 - 25, 248, 50, this);
        optionButton = new OptionButton(main.WIDTH/2 - 124, main.HEIGHT/2 -50 - 25, 248, 50,this);
        quit = new QuitButton(main.WIDTH/2 - 124 , main.HEIGHT/2 - 100 - 25, 248, 50, this);

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);
        stage.addActor(quit);
        stage.addActor(optionButton);
        stage.addActor(singlePlayerButton);
        stage.addActor(multiPlayerButton);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when this screen becomes the current screen for a game
     */
    @Override
    public void show() {
        singlePlayerButton.setVisible(true);
        multiPlayerButton.setVisible(true);
        optionButton.setVisible(true);
        quit.setVisible(true);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        main.batch.end();
        if(optionUI!=null) {
            optionUI.render();
        }
        stage.draw();
    }

    /**
     * @param width the new width
     * @param height the new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * pauses
     */
    @Override
    public void pause() {

    }

    /**
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a game
     */
    @Override
    public void hide() {
        singlePlayerButton.setVisible(false);
        multiPlayerButton.setVisible(false);
        optionButton.setVisible(false);
        quit.setVisible(false);
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        if(optionUI!=null) {
            optionUI.disposeOptionsUI();
        }
    }

    /**
     * create the option ui, if it does not yet exist
     */
    public void createOptions(){
        if(optionUI==null) {
            hide();
            optionUI = new OptionUI(main, stage, null, this);
        }
    }

    /**
     * remove the option ui
     */
    public void deleteOptions(){
        show();
        optionUI = null;
    }

    /**
     * return the option ui
     * @return the option ui
     */
    public OptionUI getOptionUI() {
        return optionUI;
    }

    /**
     * create a general option ui, if it does not yet exist
     */
    public void createGeneralUI() {
        if(generalUI == null) {
            generalUI = new GeneralUI(main, stage, null, this);
        }
    }

    /**
     * remove the general option ui
     */
    public void deleteGeneralUI() {
        generalUI = null;
    }

    /**
     * create a video option ui, if it does not yet exist
     */
    public void createVideoUI() {
        if(videoUI == null) {
            videoUI = new VideoUI(main, stage, null, this);
        }
    }

    /**
     * delete the video option ui
     */
    public void deleteVideoUI() {
        videoUI = null;
    }

    /**
     * create an audio option ui, if it does not yet exist
     */
    public void createAudioUI(){
        if(audioUI == null) {
            audioUI = new AudioUI(main, stage, null, this);
        }
    }

    /**
     * delete the audio option ui
     */
    public void deleteAudioUI(){
        audioUI = null;
    }

    /**
     * create a credit ui, if it does not yet exist
     */
    public void createCreditUI(){
        if(creditsUI == null) {
            creditsUI = new CreditsUI(main, stage, null, this);
        }
    }

    /**
     * delete the credit ui
     */
    public void deleteCreditUI(){
        creditsUI = null;
    }

    /**
     * create the control option ui, if it does not yet exist
     */
    public void createControlUI(){
        if(controlUI == null) {
            controlUI = new ControlUI(main, stage, null, this);
        }
    }

    /**
     * delete the control option ui
     */
    public void deleteControlUI(){
        controlUI = null;
    }

    /**
     * resumes the existing game.
     * called by button
     */
    public void resumeGame() {

    }

    /**
     * start/continue a multi player game
     */
    public void setMultiplayer() {
        //controller call
        main.setScreen(new SPNewOrResume(main, false));
        dispose();
    }

    /**
     * start/continue a single player game
     */
    public void setSingleplayer() {
        //controller call
        main.setScreen(new SPNewOrResume(main, true));
        dispose();
    }

    /**
     * quits.
     * called by button
     */
    public void quit() {
        Gdx.app.exit();
    }


}
