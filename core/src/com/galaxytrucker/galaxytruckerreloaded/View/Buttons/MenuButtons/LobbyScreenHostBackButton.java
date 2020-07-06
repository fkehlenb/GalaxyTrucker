package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.LobbyScreenHost;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.SelectLobbyScreen;

public class LobbyScreenHostBackButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    private Main main;

    /**
     * the screen this button is on
     */
    private LobbyScreenHost screen;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        screen.dispose();
        main.setScreen(new SelectLobbyScreen(main));
    }

    /**
     * constructor
     *
     * @param screen  the screen this button is on
     */
    public LobbyScreenHostBackButton(float x, float y, float width, float height, LobbyScreenHost screen, Main main) {
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
