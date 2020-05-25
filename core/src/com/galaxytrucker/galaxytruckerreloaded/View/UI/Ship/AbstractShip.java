package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.RoomUI;

public abstract class AbstractShip {

    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * ship used by this view
     */
    private Ship ship;

    /**
     * the rooms of the ship
     */
    private List<RoomUI> rooms;

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
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     */
    public AbstractShip(Main main, Ship ship) {
    }
}
