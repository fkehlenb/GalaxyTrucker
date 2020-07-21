package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.EnemyShipInfo.EnemyHullUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.EnemyShipInfo.EnemySystemUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.RoomUI;

import java.util.HashMap;
import java.util.List;

public class EnemyShip extends AbstractShip {

    /**
     * battle enemy box
     */
    private Texture enemyBackground;

    /**
     * the base texture for the enemy ship
     */
    private Texture enemyShipTexture;

    /**
     * the grey room background texture
     */
    private Texture enemyRoomBackgroundTexture;

    /**
     * the uis of the systems belonging to the enemy ship
     */
    private List<EnemySystemUI> systems;

    /**
     * x position this is starting at
     */
    private float x;

    /**
     * y position this is starting at
     */
    private float y;

    /**
     * the hashmap of the rooms and their uis
     */
    private HashMap<Integer, RoomUI> roomUIHashMap;

    /**
     * the hull status ui of the enemy ship
     */
    private EnemyHullUI hull;

   /* public EnemyShip(Main main, Ship ship, Stage stage, GamePlay game, Texture enemyBackground, List<EnemySystemUI> systems, EnemyHullUI hull) {
        super(main, ship, stage, game);
        this.enemyBackground = new Texture("battle/battle_overlay.png");
        // Systeme des Schiffes in SystemUI umwandeln
        this.systems = systems;
        this.hull = hull;
        x = main.WIDTH - enemyBackground.getWidth() - main.WIDTH/6;
        y = main.HEIGHT/2 - enemyBackground.getHeight()/2;
    }*/

    /**
     * Constructor
     * TODO methods to access all shipinfo stuff
     * @param main - the main class for SpriteBatch
     */
    public EnemyShip(Main main, Ship ship, Stage stage, GamePlay game, Stage tileStage) {
        super(main, ship, stage, game, tileStage);
        enemyBackground = new Texture("battle/battle_overlay.png");
        enemyShipTexture = new Texture("ship/" + ship.getShipType().toString().toLowerCase() + "base.png");
        enemyRoomBackgroundTexture = new Texture("ship/" + ship.getShipType().toString().toLowerCase() + "floor.png");

        x = Main.WIDTH - enemyBackground.getWidth() - 20;
        y = Main.HEIGHT/2f - enemyBackground.getHeight()/2f;

        roomUIHashMap = new HashMap<>();
        List<Room> existingRooms = ship.getSystems();
        for(Room r : existingRooms) { //TODO bx, by
            if(r instanceof System) {
                roomUIHashMap.put(r.getId(), new EnemySystemUI(main, r, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), x), getRoomY(ship.getShipType(), r.getInteriorID(), y), (System) r));
            }
            else {
                roomUIHashMap.put(r.getId(), new RoomUI(main, r, tileStage, this, getRoomX(ship.getShipType(), r.getInteriorID(), x), getRoomY(ship.getShipType(), r.getInteriorID(), y)));
            }
        }
    }

    /**
     * the room was chosen by the player using a tile button
     *
     * @param room the room that was chosen
     */
    @Override
    public void roomChosen(Room room) {

    }

    /**
     * to render the ui
     */
    @Override
    public void render() {
        main.batch.begin();
        main.batch.draw(enemyBackground, x, y, enemyBackground.getWidth(), enemyBackground.getHeight());
        main.batch.end();
    }

    /**
     * show the ship
     */
    @Override
    public void showShipView() {

    }

    /**
     * hide the ship
     */
    @Override
    public void hideShipView() {

    }

    /**
     * dispose of ship
     */
    @Override
    public void disposeShipView() {
        enemyBackground.dispose();
        
    }

    /**
     * a status of a system was updated and needs to be properly displayed
     * eg system hit
     * @param damage the damage to the system
     */
    public void systemStatusUpdate(int damage) {

    }

    /**
     * the hull was hit and the status of it needs to be updated
     */
    public void hullHit() {

    }

    /**
     * animation of the ship being destroyed
     */
    public void shipDestroyedAnimation() {

    }

    /**
     * animation of the ship fleeing
     */
    public void shipFleeingAnimation() {

    }

    /**
     * update of the hull status (hp)
     *
     * @param hpvalue new status
     */
    @Override
    public void hullStatusUpdate(int hpvalue) {
        hull.hullStatusUpdate(hpvalue);
    }

    /**
     * shield status update
     *
     * @param shieldvalue new status
     */
    @Override
    public void shieldStatusUpdate(int shieldvalue) {

    }

}
