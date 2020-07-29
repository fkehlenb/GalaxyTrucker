package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.CrewUI;

/**
 * Button for selecting a crew member
 */
public class CrewSelectButton extends ImButton {

    /**
     * the ui with this button
     */
    private CrewUI ui;

    /**
     * constructor
     * @param texture texture for when the button is not chosen
     * @param texture1 texture for when the button is chosen
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param ui ui this button is on
     */
    public CrewSelectButton(Texture texture, Texture texture1, float x, float y, float width, float height, CrewUI ui) {
        super(texture, texture, texture1, x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * left click action
     */
    public void leftClick()
    {
        ui.crewMoving();
        this.setChecked(true);
    }

    /**
     * set the texture to the one for when the button is not chosen
     */
    public void moved() {
        this.setChecked(false);
    }
}

