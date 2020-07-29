package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.CreateOrJoinServer;

public class StartServerButton extends ImButton {

    /**
     * the screen this button is on
     */
    private CreateOrJoinServer screen;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param screen screen the button is on
     */
    public StartServerButton (float x, float y, float width, float height, CreateOrJoinServer screen) {
        super(new Texture("buttons/start_server_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * choose to start the server (be the host)
     */
    @Override
    public void leftClick()
    {
        screen.startServer();
    }

}
