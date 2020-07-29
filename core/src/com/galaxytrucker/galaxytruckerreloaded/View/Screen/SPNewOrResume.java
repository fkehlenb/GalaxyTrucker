package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.*;

/**
 * Selection screen new game or resuming of old one
 */
public class SPNewOrResume extends MenuScreen {


    /** Constructor  */
    public SPNewOrResume(Main main){
        super(main);

        StartNewGameButton startNewGameButton = new StartNewGameButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        ResumeButton resumeButton = new ResumeButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*1, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        BackButton backButton = new BackButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);

        stage.addActor(startNewGameButton);
        stage.addActor(resumeButton);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    /***
     * return to last screen
     */
    @Override
    public void goBack() {
        main.setScreen(new MainMenu(main));
        dispose();
    }

    /**
     * render everything
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
    }

    /**
     * resume an old game
     */
    public void resumeGame() {
        if(!main.isMultiplayer()) {
            main.setScreen(new LoginScreen(main));
        }
        else {
            main.setScreen(new LoginScreen(main));
        }
        dispose();
    }

    /**
     * start a new game
     */
    public void newGame() {
        if(!main.isMultiplayer()) {
            main.setScreen(new ChooseDifficultyScreen(main));
        }
        else {
            main.setScreen(new ChooseDifficultyScreen(main));
        }
        dispose();
    }

}
