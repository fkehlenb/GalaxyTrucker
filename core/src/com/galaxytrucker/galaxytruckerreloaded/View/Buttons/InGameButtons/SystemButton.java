package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.SubsystemUI;

/**
 * Button for regulation of (Sub)-System energie supplyment
 */
public class SystemButton extends ImButton
{
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui this button belongs to
     */
    private SubsystemUI ui;

    /**
     * Constructor
     *
     * @param ui the ui this button belongs to
     */
    public SystemButton(Texture texture, float x, float y, float width, float height, SubsystemUI ui) {
        super(texture, x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(event.getButton() == Input.Buttons.RIGHT) {
                    rightClick();
                }
                else if(event.getButton() == Input.Buttons.LEFT) {
                    leftClick();
                }
            }
        });
    }


    /**
     * decrease the Energy provided for a System
     */
    public void rightClick()
    {
        ui.removeEnergy(); //TODO how much?
    }

    /**
     * increases the Energie provided for a System
     */
    public void leftClick() {
        ui.addEnergy();
    }
}
