package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.PVPOpponents;

/**
 * button for closing the pvp ui
 */
public class PVPCloseButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the screen this button is on
     */
    private PVPOpponents screen;

    /**
     * the constructor
     * @param x the x position
     * @param y the y position
     * @param width the width
     * @param height the height
     * @param screen the screen
     */
    public PVPCloseButton(float x, float y, float width, float height, PVPOpponents screen) {
        super(new Texture("close_on.png"), x, y, width, height);
        this.screen = screen;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    @Override
    public void leftClick() {
        screen.disposePVPOpponents();
    }
}
