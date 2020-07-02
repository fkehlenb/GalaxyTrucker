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
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenuGameModeScreen;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.SPNewOrResume;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ShipSelector;


public class SPNewOrResumeBackButton extends ImButton {
    /**
     * Click sound effect
     */
    private Sound clickSound;

    private Main main;

    /**
     * the screen this button is on
     */
    private SPNewOrResume screen;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.dispose();
        main.setScreen(new MainMenuGameModeScreen(main));
    }

    /**
     * constructor
     *
     * @param screen  the screen this button is on
     */
    public SPNewOrResumeBackButton(float x, float y, float width, float height, SPNewOrResume screen, Main main) {
        super(new Texture("buttons/back_button.png"), x, y, width, height);
        this.screen = screen;
        this.main = main;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}

