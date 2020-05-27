package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.LoginScreen;
import sun.rmi.runtime.Log;

/**
 * the login button for the login screen
 */
public class LoginButton extends Button {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;
    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;
    /**
     * Background
     */
    private Texture background;
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the screen from which this was called
     */
    private LoginScreen screen;

    /**
     * Left-Click action of the Button.
     * calls method in the screen
     */
    @Override
    public void leftClick() {

    }

    /**
     * the constructor
     * @param main the main class
     * @param screen the login screen this button belongs to
     */
    public LoginButton(Main main, LoginScreen screen) {

    }
}
