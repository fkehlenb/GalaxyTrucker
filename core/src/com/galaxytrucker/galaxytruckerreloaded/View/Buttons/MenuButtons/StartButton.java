package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.LobbyScreenHost;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

/**
 * Starts a new Game
 */
public class StartButton extends ImButton
{
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the screen this button is on
     */
    private LobbyScreenHost screen;

    /**
     * Constructor
     *
     * @param screen the screen this button is on
     */
    public StartButton(float x, float y, float width, float height, LobbyScreenHost screen) {
        super(new Texture("continue.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * Creates s a new Instance of PLayer an an new Ship
     */
    public void leftClick()
    {
        screen.resumeGame();
    }
}