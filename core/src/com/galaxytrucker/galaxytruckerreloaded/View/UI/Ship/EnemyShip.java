package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.EnemyShipInfo.EnemyHullUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.EnemyShipInfo.EnemySystemUI;

public class EnemyShip extends AbstractShip {

    /**
     * background of this enemy ship
     */
    private Texture enemyBackground;

    /**
     * the uis of the systems belonging to the enemy ship
     */
    private List<EnemySystemUI> systems;

    private float x;
    private float y;

    /**
     * the hull status ui of the enemy ship
     */
    private EnemyHullUI hull;

    public EnemyShip(Main main, Ship ship, Stage stage, GamePlay game, Texture enemyBackground, List<EnemySystemUI> systems, EnemyHullUI hull) {
        super(main, ship, stage, game);
        this.enemyBackground = new Texture("battle/battle_overlay.png");
        //TODO Systeme des Schiffes in SystemUI umwandeln
        this.systems = systems;
        this.hull = hull;
        x = main.WIDTH - enemyBackground.getWidth() - main.WIDTH/6;
        y = main.HEIGHT/2 - enemyBackground.getHeight()/2;
    }

    /**
     * to render the ui
     */
    @Override
    public void render() {
        main.batch.begin();
        main.batch.draw(enemyBackground, x, y, 400, 700);
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

        
    }

    /**
     * a status of a system was updated and needs to be properly displayed
     * eg system hit
     * TODO add parameter which system it is OR each controller gets one ui thing, in which case the whole relations here need to be destroyed
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

    }

    /**
     * shield status update
     *
     * @param shieldvalue new status
     */
    @Override
    public void shieldStatusUpdate(int shieldvalue) {

    }


    /**
     * Constructor
     * TODO methods to access all shipinfo stuff
     * @param main - the main class for SpriteBatch
     */
    public EnemyShip(Main main, Ship ship, Stage stage, GamePlay game) {
        super(main, ship, stage, game);
    }
}
