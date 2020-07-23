package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
     * the texture region using the enemy ship texture
     * needed to rotate the texture easier
     */
    private TextureRegion enemyShipTextureRegion;

    /**
     * the texture region using the enemy room background texture
     * needed to rotate the texture easier
     */
    private TextureRegion enemyRoomBackgroundTextureRegion;

    /**
     * x position this is starting at
     */
    private float x;

    /**
     * y position this is starting at
     */
    private float y;

    /**
     * the width and height used for drawin
     */
    private float width, height;

    /**
     * width and height used for drawing the room background(grey thingy)
     */
    private float roomWidth, roomHeight;

    /**
     * the width and height of the box around the enemy ship
     */
    private float boxWidth, boxHeight;

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
        Texture enemyShipTexture = new Texture("ship/" + ship.getShipType().toString().toLowerCase() + "base.png");
        Texture enemyRoomBackgroundTexture = new Texture("ship/" + ship.getShipType().toString().toLowerCase() + "floor.png");
        enemyShipTextureRegion = new TextureRegion(enemyShipTexture);
        enemyRoomBackgroundTextureRegion = new TextureRegion(enemyRoomBackgroundTexture);
        enemyShipTextureRegion.flip(false, true);
        enemyRoomBackgroundTextureRegion.flip(false, true);

        width = enemyShipTexture.getWidth()*1.5f;
        height = enemyShipTexture.getHeight()*1.5f;
        roomWidth = enemyRoomBackgroundTexture.getWidth()*1.5f;
        roomHeight = enemyRoomBackgroundTexture.getHeight()*1.5f;
        boxWidth = enemyBackground.getWidth()*1.5f;
        boxHeight = Main.HEIGHT - 20;
        x = Main.WIDTH - boxWidth - 20;
        y = Main.HEIGHT/2f - boxHeight/2f;

        //the base x and y for the rooms, meaning the middle of the ship
        float tileX = x + width/2;
        float tileY = Main.HEIGHT/2f;

        roomUIHashMap = new HashMap<>();
        List<Room> existingRooms = ship.getSystems();
        for(Room r : existingRooms) {
            if(r instanceof System) {
                roomUIHashMap.put(r.getId(), new EnemySystemUI(main, r, tileStage, this, getRoomY(ship.getShipType(), r.getInteriorID(), tileX), getRoomX(ship.getShipType(), r.getInteriorID(), tileY), (System) r));
            }
            else {
                roomUIHashMap.put(r.getId(), new RoomUI(main, r, tileStage, this, getRoomY(ship.getShipType(), r.getInteriorID(), tileX), getRoomX(ship.getShipType(), r.getInteriorID(), tileY)));
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
        game.roomChosenAsTarget(room);
    }

    /**
     * to render the ui
     */
    @Override
    public void render() {
        render1();
        tileStage.draw();
        render2();
    }

    /**
     * rendering everything up to the tile stage
     * seperation neeeded because tilestage is shared with the player ship
     */
    public void render1() {
        main.batch.begin();
        main.batch.draw(enemyBackground, x, y, boxWidth, boxHeight);
        main.batch.draw(enemyShipTextureRegion, x, Main.HEIGHT/2f - height/2f, width/2, height/2, width, height, 1, 1, 90);
        main.batch.draw(enemyRoomBackgroundTextureRegion, (x+width/2) - roomWidth/2, Main.HEIGHT/2f - roomHeight/2f, roomWidth/2, roomHeight/2, roomWidth, roomHeight, 1, 1, 90);
        main.batch.end();
    }

    /**
     * rendering everything after the tile stage
     */
    public void render2() {
        for(RoomUI r : roomUIHashMap.values()) {
            r.render();
        }
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
