package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.RoomUI;

public class TileButton extends ImButton {

    /**
     * the click sound
     */
    private Sound clickSound;

    private RoomUI ui;

    @Override
    public void leftClick() {
        ui.chosen();
    }

    public TileButton(float x, float y, float width, float height, RoomUI ui) {
        super(new Texture("ship/tile.png"), x, y, width, height);
        this.ui = ui;

        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });

        this.getImage().setFillParent(true);
    }
}
