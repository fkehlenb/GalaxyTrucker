package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.CreateOrJoinServer;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.LoginScreen;

public class JoinServerButton extends ImButton {

    /**
     * the screen this button is on
     */
    private CreateOrJoinServer screen;

    /**
     * the screen this button is on, if login
     */
    private LoginScreen loginScreen;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen screen the button is on
     */
    public JoinServerButton (float x, float y, float width, float height, CreateOrJoinServer screen) {
        super(new Texture("buttons/join_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen screen the button is on
     */
    public JoinServerButton (float x, float y, float width, float height, LoginScreen screen) {
        super(new Texture("buttons/join_button.png"), x, y, width, height);
        this.loginScreen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * choose to join a server
     */
    @Override
    public void leftClick()
    {
        if(screen != null) {
            screen.joinServer();
        }
        else if(loginScreen != null) {
            loginScreen.joinServer();
        }
    }

}
