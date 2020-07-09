package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.CreateOrJoinServer;

public class JoinServerButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the screen this button is on
     */
    private CreateOrJoinServer screen;

    /**
     * Constructor
     *
     * @param screen the screen this button is on
     */
    public JoinServerButton (float x, float y, float width, float height, CreateOrJoinServer screen) {
        super(new Texture("ingame_continue.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * Sets difficutly to a specific level
     */
    @Override
    public void leftClick()
    {
        screen.joinServer();
    }

}
