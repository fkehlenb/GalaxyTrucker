package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

/**
 * button used to move the ship. upper middle corner, opens the map
 */
public class MoveButton extends ImButton {

    /**
     * the ui this button is on
     */
    private ShipView ui;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        ui.openMap();
    }

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param ui ui this button is on
     */
    public MoveButton(float x, float y, float width, float height, ShipView ui) {
        super(new Texture("FTL_JUMP.png"), x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
