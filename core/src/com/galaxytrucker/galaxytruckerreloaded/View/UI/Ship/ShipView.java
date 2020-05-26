package com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.AutofireButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MoveButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ShipButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory.InventoryUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.*;

public class ShipView extends AbstractShip {

    /**
     * the uis of all the crew members
     */
    private List<CrewUI> crew;

    /**
     * the energy ui of this ship
     */
    private EnergyUI energy;

    /**
     * the hull ui of this ship
     */
    private HullUI hull;

    /**
     * the ui displaying the amount of money the player has
     */
    private ScrapUI money;

    /**
     * button to open the inventory (InventoryUI)
     */
    private ShipButton inventory;

    /**
     * button on the upper center
     * after the button is clicked, the map opens and a target needs to be selected
     */
    private MoveButton moveButton;

    /**
     * the background texture of the ship
     */
    private Texture shipBackground;

    /**
     * the weapon autofire button
     */
    private AutofireButton weaponAutofire;

    /**
     * the general background for the weapon display in the bottom left corner next to the energy status display
     */
    private Texture weaponGeneralBackground;

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Show the ship
     */
    public void showShipView() {
    }
    
    /**
     * Hide the ship
     */
    public void hideShipView() {
    }

    /**
     * Dispose of ship
     */
    public void disposeShipView() {
    }

    /**
     * Ship hop animation
     */
    private void hyperspeedHopAnimation() {
    }

    /**
     * Shield destroyed animation
     */
    private void shipDestroyedAnimation() {
    }

    /**
     * Constructor
     *
     * @param main - the main class for SpriteBatch
     */
    public ShipView(Main main, Ship ship) {
        super(main, ship);
    }
}
