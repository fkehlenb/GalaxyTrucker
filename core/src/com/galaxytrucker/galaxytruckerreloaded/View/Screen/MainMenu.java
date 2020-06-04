package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * Main menu screen
 */
public class MainMenu extends State {

    private Texture background;
    private Texture newGame;
    /** Constructor  */
    public MainMenu(GameStateManager gsm){
        super(gsm);
        background = new Texture("1080p.png");
        newGame = new Texture("start_select2.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(newGame, Main.WIDTH/2 - newGame.getWidth()/2,Main.HEIGHT/2, 248,50);
        sb.end();
    }

    /*   *//**
     * The sprite batch
     *//*
    private SpriteBatch batch;

    *//**
     * Orthographic camera
     *//*
    private OrthographicCamera camera;

    *//**
     * The screen texture
     *//*
    private Texture background;

    *//**
     * new game button. leads to shipselector
     *//*
    private NewGameButton newGame;

    *//**
     * start game button. continues old game
     *//*
    private StartButton startGame;

    *//**
     * quit button
     *//*
    private QuitButton quitButton;

    *//**
     * Looping music track
     *//*
    private Music music;

    *//**
     * Click sound effect
     *//*
    private Sound clickSound;*/

    /**
     * starts a new game.
     * called by button
     */
    public void newGame() {

    }

    /**
     * resumes the existing game.
     * called by button
     */
    public void resumeGame() {

    }

    /**
     * quits.
     * called by button
     */
    public void quit()  {

    }


}
