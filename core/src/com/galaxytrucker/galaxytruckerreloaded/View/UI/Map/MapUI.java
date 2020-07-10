package com.galaxytrucker.galaxytruckerreloaded.View.UI.Map;

import com.badlogic.gdx.graphics.Texture;
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

    private Overworld map;
    private float x;
    private float y;

    /**
     * Constructor
     * @param main - main class
     */
    public MapUI(Main main, Stage stage, Overworld map, ShipView shipView) {
        this.main = main;
        this.stage = stage;
        this.shipView = shipView;
        this.map = main.getClient().getOverworld();

        mapTexture = new Texture("map/map_overlay.png");

        x = main.WIDTH/2 - mapTexture.getWidth()/2;
        y = main.HEIGHT/2 - mapTexture.getHeight()/2;

        locations = new LinkedList<>();
        //TODO
        for(Planet f : map.getPlanetMap()) {
            float fx = f.getPosX();
            float fy = f.getPosY();
           MapButton mb = new MapButton(new Texture("map/map_button.png"), (x+fx), (y+fy), 20, 20, this, f);
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
        main.batch.draw(mapTexture, x, y, 1160, 626);
        main.batch.end();
    }

    /**
     * Dispose of ui
     */
    public void disposeMapUI() {
        mapTexture.dispose();
        for(MapButton m : locations) {
            m.remove();
        }
        shipView.deleteMap();

    }

    /**
     * move to new planet
     * called by mapbutton
     * @param planet the new planet
     */
    public void moveToPlanet(Planet planet) {
        boolean success = shipView.travel(planet);
        if(success) {
            disposeMapUI();
        }
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
