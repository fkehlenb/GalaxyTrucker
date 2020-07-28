package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Map.MapUI;

/**
 * button representing one planet on the map
 */
public class MapButton extends ImButton {

    /**
     * planet
     */
    private Planet planet;

    /**
     * the ui this is on
     */
    private MapUI ui;

    /**
     * constructor
     * @param texture texture of button
     * @param x x pos of button
     * @param y y pos of button
     * @param width width of button
     * @param height height of button
     * @param ui ui this button is on
     * @param planet planet this button is associated with
     */
    public MapButton(Texture texture, float x, float y, float width, float height, MapUI ui, Planet planet) {
        super(texture, x, y, width, height);
        this.ui = ui;
        this.planet = planet;

        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * what happens on left click (method in UI)
     */
    @Override
    public void leftClick() {
        ui.moveToPlanet(planet);
    }
}
