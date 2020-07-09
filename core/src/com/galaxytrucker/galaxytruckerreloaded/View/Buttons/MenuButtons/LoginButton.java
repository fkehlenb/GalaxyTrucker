package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.LoginScreen;

/**
 * the login button for the login screen
 */
public class LoginButton extends ImButton {

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
        screen.login();
    }

    /**
     * the constructor
     * @param screen the login screen this button belongs to
     */
    public LoginButton(float x, float y, float width, float height, LoginScreen screen) {
        super(new Texture("buttons/continue_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
