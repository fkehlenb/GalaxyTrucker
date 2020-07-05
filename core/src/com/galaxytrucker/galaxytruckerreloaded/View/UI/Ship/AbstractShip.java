package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;


import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.RoomUI;

public abstract class AbstractShip {

    /**
     * HP
     */
    protected int hp;

    /**
     * Shields
     */
    protected int shields;

    /**
     * the id of the ship
     */
    protected int id;

    protected Main main;

    protected Stage stage;

    protected GamePlay game;

    /**
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     */
    public AbstractShip(Main main, Ship ship, Stage stage, GamePlay game) {
        this.main = main;
        this.stage = stage;
        this.game = game;
        hp = ship.getHp();
        shields = ship.getShields();
        id = ship.getId();
    }

    /**
     * to render the ui
     */
    public abstract void render();

    /**
     * show the ship
     */
    public abstract void showShipView();

    /**
     * hide the ship
     */
    public abstract void hideShipView();

    /**
     * dispose of ship
     */
    public abstract void disposeShipView();

    /**
     * update of the hull status (hp)
     * @param hpvalue new status
     */
    public abstract void hullStatusUpdate(int hpvalue);

    /**
     * shield status update
     * @param shieldvalue new status
     */
    public abstract void shieldStatusUpdate(int shieldvalue);

}
