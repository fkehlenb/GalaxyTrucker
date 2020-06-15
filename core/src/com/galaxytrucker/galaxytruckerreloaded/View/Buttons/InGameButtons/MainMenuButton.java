package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.GameOver;

/***
 * button used to return to the main menu
 */
public class MainMenuButton extends ImButton {
    /**
     * Click sound effect
     */
    private Sound clickSound;

    private Main main;

    /**
     * Left-Click action of the Button. opens the main menu screen
     */
    @Override
    public void leftClick() {
        main.setScreen(new MainMenu(main));
    }

    /**
     * constructor
     */
    public MainMenuButton(float x, float y, float width, float height, Main main) {
        super(new Texture("sd"), x, y, width, height);
        this.main = main;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
