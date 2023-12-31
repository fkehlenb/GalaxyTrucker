package com.galaxytrucker.galaxytruckerreloaded.View.UI.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.PlanetEventController;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.InventoryCloseButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MapButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

import java.util.LinkedList;
import java.util.List;

public class MapUI {

    /**
     * Map texture background
     */
    private Texture mapTexture;

    /**
     * the mapbuttons representing the locations on the map
     */
    private List<MapButton> locations;

    /**
     * Button to close the map
     */
    private InventoryCloseButton closeButton;

    /**
     * main class extending game
     */
    private Main main;

    /**
     * the ui this is a part of
     */
    private ShipView shipView;

    /**
     * the x position
     */
    private float x;

    /**
     * the y position
     */
    private float y;

    /**
     * Constructor
     * @param main - main class
     */
    public MapUI(Main main, Stage stage, ShipView shipView) {
        this.main = main;
        this.shipView = shipView;

        Overworld map = ClientControllerCommunicator.getInstance(null).getMap();
        Ship ship = PlanetEventController.getInstance(null).getClientShip();

        mapTexture = new Texture("map/map_overlay.png");

        x = Main.WIDTH/2f - mapTexture.getWidth()/2f;
        y = Main.HEIGHT/2f - mapTexture.getHeight()/2f;
        float xDist = Main.WIDTH/(1920/150f);
        float yDist = Main.HEIGHT/(1080/75f);

        closeButton = new InventoryCloseButton(x+(Main.WIDTH/2.01f), y-(Main.HEIGHT/13.5f), Main.WIDTH/7.742f, Main.HEIGHT/21.6f, null, null, this);
        stage.addActor(closeButton);

        locations = new LinkedList<>();
        for(Planet f : map.getPlanetMap()) {
            float fx = f.getPosX();
            float fy = f.getPosY();

            //int mapSize = map.getPlanetMap().size()*2;
            //StartPlanet Extra
            if(fx == -1 && fy == -1){
                 //Start
                MapButton mb;
                if (ship.getPlanet().getPosX() == fx && ship.getPlanet().getPosY() == fy){
                    mb = new MapButton(new Texture("map/map_icon_ship_2.png"), (x+Main.WIDTH/(1920/100f)), (y+2*yDist), Main.WIDTH/(1920/30f), Main.HEIGHT/(1080/30f), this, f);
                } else {
                    mb = new MapButton(new Texture("map/map_button.png"), (x + Main.WIDTH / (1920 / 100f)), (y + 2 * yDist), Main.WIDTH / (1920 / 20f), Main.HEIGHT / (1080 / 20f), this, f);
                }
                locations.add(mb);
                stage.addActor(mb);
            }
            else if (fx == 30 && fy == 30){
                //Boss
                MapButton mb;
                if (ship.getPlanet().getPosX() == fx && ship.getPlanet().getPosY() == fy){
                    mb = new MapButton(new Texture("map/map_icon_ship_2.png"), (x+5*xDist+Main.WIDTH/(1920/120f)), (y+2*Main.HEIGHT/(1080/50f)), Main.WIDTH/(1920/20f), Main.HEIGHT/(1080/20f), this, f);
                } else {
                    mb = new MapButton(new Texture("map/map_icon_boss.png"), (x+5*xDist+Main.WIDTH/(1920/120f)), (y+2*Main.HEIGHT/(1080/50f)), Main.WIDTH/(1920/70f), Main.HEIGHT/(1080/70f), this, f);
                }
                locations.add(mb);
                stage.addActor(mb);
            }
            else {
                //Alle anderen
                MapButton mb;
                if (ship.getPlanet().getPosX() == fx && ship.getPlanet().getPosY() == fy){
                    mb = new MapButton(new Texture("map/map_icon_ship_2.png"), (x+fx*xDist+Main.WIDTH/(1920/150f)), (y+fy*yDist), Main.WIDTH/(1920/30f), Main.HEIGHT/(1080/30f), this, f);
                } else {
                    mb = new MapButton(new Texture("map/map_button.png"), (x+fx*xDist+Main.WIDTH/(1920/150f)), (y+fy*yDist), Main.WIDTH/(1920/20f), Main.HEIGHT/(1080/20f), this, f);
                }
                locations.add(mb);
                stage.addActor(mb);
            }
        }
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(mapTexture, x, y-Main.HEIGHT/(10.8f), Main.WIDTH/1.655f, Main.HEIGHT/(1.725f));
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
        closeButton.remove();
    }

    /**
     * move to new planet
     * called by mapbutton
     * @param planet the new planet
     */
    public void moveToPlanet(Planet planet) {
        boolean success = shipView.travel(planet);
        if(success) {
            
            shipView.changeAmountFuel(-1);
            disposeMapUI();
            closeButton.remove();
        }
    }
}
