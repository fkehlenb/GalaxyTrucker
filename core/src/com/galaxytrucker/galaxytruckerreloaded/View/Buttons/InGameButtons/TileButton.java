package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.RoomUI;

/**
 * button for representing one tile
 */
public class TileButton extends ImButton {

    /**
     * ui this button is on
     */
    private RoomUI ui;

    @Override
    public void leftClick() {
        ui.chosen();
    }

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param ui ui this button is on
     */
    public TileButton(float x, float y, float width, float height, RoomUI ui) {
        super(new Texture("ship/tile.png"), new Texture("ship/tile.png"), new Texture("ship/tilebreach.png"), x, y, width, height);
        this.ui = ui;

        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });

        this.getImage().setFillParent(true);
    }

    /**
     * set texture to the one for when the button is "chosen"
     * used to display breaches here
     */
    public void breach() {
        this.setChecked(true);
    }

    /**
     * set texture to the one for when the button is not "chosen"
     * used to display breaches here
     */
    public void breachGone() {
        this.setChecked(false);
    }
}
