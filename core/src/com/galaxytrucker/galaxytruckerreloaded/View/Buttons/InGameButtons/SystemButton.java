package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.SubsystemUI;

/**
 * Button for regulation of (Sub)-System energie supplyment
 */
public class SystemButton extends ImButton
{

    /**
     * the ui this button belongs to
     */
    private SubsystemUI ui;

    /**
     * constructor
     * @param texture texture for this button
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param ui ui this button is on
     */
    public SystemButton(Texture texture, float x, float y, float width, float height, SubsystemUI ui) {
        super(texture, x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener(Input.Buttons.LEFT) {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
        this.addListener(new ClickListener(Input.Buttons.RIGHT) {
            public void clicked(InputEvent event, float x, float y) {
                rightClick();
            }
        });
    }


    /**
     * decrease the Energy provided for a System
     */
    public void rightClick()
    {
        ui.removeEnergy();
    }

    /**
     * increases the Energie provided for a System
     */
    public void leftClick() {
        ui.addEnergy();
    }
}
