package com.galaxytrucker.galaxytruckerreloaded.View.UI.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MapButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

import java.util.LinkedList;
import java.util.List;

public class MapUI {

    /**
     * Map texture background
     */
    private Texture mapTexture;

    private List<MapButton> locations;

    private Main main;

    private Stage stage;

    private ShipView shipView;

    /**
     * Constructor
     * @param main - main class
     */
    public MapUI(Main main, Stage stage, Overworld map, ShipView shipView) {
        this.main = main;
        this.stage = stage;
        this.shipView = shipView;

        mapTexture = new Texture("map/map_overlay.png");

        locations = new LinkedList<>();
        for(float[] f : map.getPlanetMap().keySet()) {
            MapButton mb = new MapButton(new Texture("map/map_button.png"), 0, 0, 0, 0, this, f[0], f[1], map.getPlanetMap().get(f));
            locations.add(mb);
            stage.addActor(mb);
        }
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(mapTexture, 0, 0, 0, 0); //TODO whxy
        main.batch.end();
    }

    /**
     * Dispose of ui
     */
    public void disposeMapUI() {
        mapTexture.dispose();
        shipView.deleteMap();
        for(MapButton m : locations) {
            m.remove();
        }
    }

    /**
     * move to new planet
     * called by mapbutton
     * @param planet the new planet
     */
    public void moveToPlanet(Planet planet) {
        //call the controller
        disposeMapUI();
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the ui
     */
    public void showMapUI() {
    }

    /**
     * hide the ui
     */
    public void hideMapUI() {
    }
}
