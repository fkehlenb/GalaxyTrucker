package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;

/***
 * button used to return to the main menu
 */
public class MainMenuButton extends ImButton {

    /**
     * main class for returning to main menu screen
     */
    private Main main;

    /**
     * Left-Click action of the Button. opens the main menu screen
     */
    @Override
    public void leftClick() {
        boolean success = ClientControllerCommunicator.getInstance(main.getClient()).logout();
        if(success) {
            main.logout();
        }
    }

    /**
     * constructo
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param main main class for switching screens
     */
    public MainMenuButton(float x, float y, float width, float height, Main main) {
        super(new Texture("control/mainmenu.png"), x, y, width, height);
        this.main = main;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
