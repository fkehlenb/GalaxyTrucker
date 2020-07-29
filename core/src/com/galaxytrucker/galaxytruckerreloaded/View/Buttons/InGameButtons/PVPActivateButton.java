package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

/**
 * button for activating pvp
 */
public class PVPActivateButton extends ImButton {

    /**
     * the screen this button is on
     */
    private GamePlay screen;

    /**
     * constructor
     * @param x the x position
     * @param y the y position
     * @param width the width
     * @param height the height
     * @param screen the screen this button is on
     */
    public PVPActivateButton(float x, float y, float width, float height, GamePlay screen) {
        super(new Texture("buttons/activate_pvp_button.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * left click action
     */
    @Override
    public void leftClick() {
        screen.activatePVP();
    }
}
