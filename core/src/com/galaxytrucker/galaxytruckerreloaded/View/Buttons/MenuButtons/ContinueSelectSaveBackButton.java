package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.ContinueSelectSave;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.SPNewOrResume;

public class ContinueSelectSaveBackButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    private Main main;

    /**
     * the screen this button is on
     */
    private ContinueSelectSave screen;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.dispose();
        main.setScreen(new SPNewOrResume(main, true));
    }

    /**
     * constructor
     *
     * @param screen  the screen this button is on
     */
    public ContinueSelectSaveBackButton(float x, float y, float width, float height, ContinueSelectSave screen, Main main) {
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
