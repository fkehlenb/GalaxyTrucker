package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

/**
 * Ends the application
 */
public class QuitButton extends ImButton
{

    /**
     * screen this button is on
     */
    private MainMenu menu;

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param menu screen the button is on
     */
    public QuitButton(float x, float y, float width, float height, MainMenu menu) {
        super(new Texture("buttons/quit_button.png"), x, y, width, height);
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });

        this.menu = menu;
    }

    /**
     * left click action
     */
    public void leftClick() {
        menu.quit();
    }

}

